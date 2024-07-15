package brew.mng.dtm.cam.web;

import brew.mng.dtm.cam.service.CollectApi;
import brew.mng.dtm.cam.service.CollectApiService;
import brew.mng.dtm.cam.service.CollectApiVO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/mng/dtm/cam")
@RequiredArgsConstructor
public class CollectApiController {

    private final CollectApiService collectApiService;

    @RequestMapping("/selectCollectApiListVw.do")
    public String selectCollectApiListVw() {
        return "/mng/dtm/cam/selectCollectApiListVw";
    }

    @RequestMapping("/selectCollectApiResponse.do")
    @ResponseBody
    public CollectApi selectCollectApiResponse(@RequestBody CollectApiVO vo) throws JSONException, IOException {
        return collectApiService.selectCollectApiResponse(vo);
    }
}
