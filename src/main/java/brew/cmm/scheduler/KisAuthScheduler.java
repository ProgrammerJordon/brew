package brew.cmm.scheduler;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewHttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KisAuthScheduler {

    private static final String PROPERTY_SOURCE_NAME = "application.properties"; // 사용할 프로퍼티
    private final ConfigurableEnvironment environment;
    private final BrewHttpUtil brewHttpUtil;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getAuthorityAccessToken() throws JSONException, IOException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/oauth2/tokenP";

        Map<String, String> headers = new HashMap<>();

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        params.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));

        StringBuilder res = brewHttpUtil.postHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.readTree(String.valueOf(res));

        String newAccessToken = "Bearer " + result.get("access_token");

        updatePorperties(PROPERTY_SOURCE_NAME, "kis.dev.accessToken", "Bearer " + newAccessToken);

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void getWebSocketAccessToken() throws JSONException, IOException {

        String url = BrewProperties.getProperty("kis.dev.url") + "/oauth2/Approval";

        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json; utf-8");

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("appkey", BrewProperties.getProperty("kis.dev.appkey"));
        params.put("appsecret", BrewProperties.getProperty("kis.dev.appsecret"));

        StringBuilder res = brewHttpUtil.postHttpRequest(url, headers, params);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode result = mapper.readTree(String.valueOf(res));

        String newAccessToken = String.valueOf(result.get("approval_key"));

        updatePorperties(PROPERTY_SOURCE_NAME, "kis.dev.approvalkey", newAccessToken);

    }

    private void updatePorperties(String propertySourceName, String propertyName, String propertyValue) {
        MutablePropertySources propertySources = environment.getPropertySources();

        PropertySource<?> propertySource = propertySources.get(propertySourceName);
        if (propertySource != null && propertySource instanceof MapPropertySource) {
            Map<String, Object> source = ((MapPropertySource) propertySource).getSource();
            source.put(propertyName, propertyValue);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(propertyName, propertyValue);
            propertySources.addFirst(new MapPropertySource(propertySourceName, map));
        }
    }
}
