package brew.mng.dtm.cam.service.impl;

import brew.cmm.util.BrewHttpUtil;
import brew.mng.dtm.cam.service.CollectApi;
import brew.mng.dtm.cam.service.CollectApiService;
import brew.mng.dtm.cam.service.CollectApiVO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service("CollectApiService")
@RequiredArgsConstructor
public class CollectApiServiceImpl implements CollectApiService {

    private final BrewHttpUtil brewHttpUtil;
    private final CollectApiDAO collectApiDAO;

    @Override
    public CollectApi selectCollectApiResponse(CollectApiVO vo) throws JSONException, IOException {

        String method = vo.getMethod();

        String url = vo.getUrl();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        Map<String, String> headers = ObejctToMap(vo.getHeaders());
        Map<String, String> params = ObejctToMap(vo.getParams());

        StringBuilder response = new StringBuilder();

        if (method.equals("GET")) {
            response = brewHttpUtil.getHttpRequest(url, headers, params);
            vo.setRes(String.valueOf(response));
        } else if(method.equals("POST")) {
            response = brewHttpUtil.postHttpRequest(url, headers, params);
            vo.setRes(String.valueOf(response));
        }
        return CollectApi.builder()
                .collectApiVO(vo)
                .build();
    }

    private Map<String, String> ObejctToMap(Object object) {
        Map<String, String> resultMap = new HashMap<>();

        if (object instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> tempMap = (Map<String, Object>) object;

            for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
                resultMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return resultMap;
    }
}
