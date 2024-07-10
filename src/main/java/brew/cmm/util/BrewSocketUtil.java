package brew.cmm.util;

import com.nimbusds.jose.shaded.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class BrewSocketUtil {

    public StringBuilder getSocket(String url, String port, Map<String, String> headers, Map<String, String> params) {

        String response = null;

        try {
            // 소켓 생성
            Socket socket = new Socket(url, Integer.parseInt(port));

            // 소켓으로부터 입력 스트림과 출력 스트림 얻기
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            // JSON 데이터 생성
            Map<String, Object> headerMap = new HashMap<>();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerMap.put(entry.getKey(), entry.getValue());
            }

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("header", headerMap);

            // 파라미터 구성
            Map<String, Object> bodyMap = new HashMap<>();
            Map<String, Object> inputMap = new HashMap<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                inputMap.put(entry.getKey(), entry.getValue());
            }
            bodyMap.put("input", inputMap);
            requestBody.put("body", bodyMap);

            // Gson을 이용하여 JSON 문자열로 변환
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(requestBody);

            // 데이터 전송
            byte[] requestBytes = jsonRequest.getBytes();
            outputStream.write(requestBytes);
            outputStream.flush();
            System.out.println("데이터 전송 완료");

            // 데이터 수신
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer); // 데이터 읽기

            if (bytesRead != -1) {
                response = new String(buffer, 0, bytesRead);
                System.out.println("서버로부터 받은 JSON 데이터: " + response);
            }
            // 소켓 종료
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new StringBuilder(response);
    }
}
