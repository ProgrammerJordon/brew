package brew.cmm.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@SuppressWarnings("unchecked")
public class BrewHttpUtil {

    public StringBuilder getHttpRequest(String url, Map<String, String> headers, Map<String, String> params) throws IOException, JSONException {
        StringBuilder requestURL = new StringBuilder(url + "?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (requestURL.length() > url.length() + 1) {
                requestURL.append("&");
            }
            requestURL.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()));
            requestURL.append("=");
            requestURL.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8.name()));
        }

        String getUrl = requestURL.toString();
        URL reqURL = new URL(getUrl);
        HttpURLConnection conn = (HttpURLConnection) reqURL.openConnection();
        conn.setRequestMethod("GET");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        StringBuilder response = new StringBuilder();

        try {
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } else {
                // 서버가 200 이외의 응답 코드를 반환하는 경우
                response.append(responseCode);
            }
        } catch (IOException e) {
            response.append("Exception occurred: ").append(e.getMessage());
        } finally {
            conn.disconnect();
        }

        return response;
    }

    public StringBuilder postHttpRequest(String url, Map<String, String> headers, Map<String, String> params) throws IOException, JSONException {
        // URL 설정
        URL reqURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) reqURL.openConnection();

        try {
            // 요청 방식 설정 (POST)
            conn.setRequestMethod("POST");

            // 헤더 추가
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // 요청 본문 데이터 설정
            conn.setDoOutput(true);

            // 파라미터를 JSON 형식으로 변환
            JSONObject jsonParams = new JSONObject(params);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonParams.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 응답 데이터 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            StringBuilder response = new StringBuilder();

            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            br.close();

            // JSON 형식으로 응답 데이터를 파싱하여 반환
            return response;

        } finally {
            // 연결 종료
            conn.disconnect();
        }
    }
}
