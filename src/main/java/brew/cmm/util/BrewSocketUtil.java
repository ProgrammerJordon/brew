package brew.cmm.util;

import com.google.gson.Gson;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint
@Component
public class BrewSocketUtil {

    private RestTemplate restTemplate;
    private static Session session;
    private static StringBuilder responseBuilder = new StringBuilder();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
        BrewSocketUtil.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message); // 값은 들어옴
        responseBuilder.append(message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected from server");
        BrewSocketUtil.session = null;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public StringBuilder getSocket(String url, Map<String, String> headers, Map<String, String> params) {

        responseBuilder = new StringBuilder(); // 매 호출마다 새로운 StringBuilder 생성

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = new URI(url);

            // 연결 시도
            container.connectToServer(this, uri);

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

            Gson gson = new Gson();
            String jsonRequest = gson.toJson(requestBody);

            session.getAsyncRemote().sendText(jsonRequest);
            System.out.println("데이터 전송 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBuilder;
    }
}