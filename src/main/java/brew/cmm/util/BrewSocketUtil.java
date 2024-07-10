package brew.cmm.util;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.websocket.*;
import org.springframework.stereotype.Component;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
@Component
public class BrewSocketUtil {

    private static CountDownLatch latch;
    private static StringBuilder responseBuilder = new StringBuilder();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        responseBuilder.append(message);
        latch.countDown(); // 서버로부터 메시지를 받으면 latch 감소
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Disconnected from server");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public StringBuilder getSocket(String url, Map<String, String> headers, Map<String, String> params) {

        latch = new CountDownLatch(1); // 메시지를 받을 때까지 대기

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = new URI(url);

            Session session = container.connectToServer(BrewSocketUtil.class, uri);

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

            // latch를 사용하여 메시지를 받을 때까지 대기
            latch.await(5, TimeUnit.SECONDS);
            // 세션 종료
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBuilder;
    }
}
