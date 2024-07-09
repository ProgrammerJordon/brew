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
public class BrewHttpUtil {

    /**
     * String url = "http://www.example.com";
     * <p>
     * Map<String, String> headers = Map.of(
     * "Authorization", "Bearer your_access_token",
     * "Content-Type", "application/json"
     * );
     * <p>
     * Map<String, String> param = Map.of(
     * "param1", "value1",
     * "param2", "value2"
     * );
     */

    public StringBuilder getHttpRequest(String url, Map<String, String> headers, Map<String, String> param) throws IOException, JSONException {

        // URL 설정 및 파라미터 추가
        StringBuilder requestURL = new StringBuilder(url + "?");

        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (requestURL.length() > url.length() + 1) {
                requestURL.append("&");
            }
            requestURL.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()));
            requestURL.append("=");
            requestURL.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8.name()));
        }

        // URL 설정
        URL reqURL = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) reqURL.openConnection();

        // 요청 방식 설정 (GET, POST 등)
        conn.setRequestMethod("GET");

        // 헤더 추가
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();

        // 응답 데이터 읽기
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuilder response = new StringBuilder();

        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        // 연결 종료
        conn.disconnect();

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
