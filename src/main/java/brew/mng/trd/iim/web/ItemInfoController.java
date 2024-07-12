package brew.mng.trd.iim.web;

import brew.mng.trd.iim.service.ItemInfo;
import brew.mng.trd.iim.service.ItemInfoService;
import brew.mng.trd.iim.service.ItemInfoVO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/mng/trd/iim")
@RequiredArgsConstructor
public class ItemInfoController {

    private final ItemInfoService itemInfoService;

    @RequestMapping("/selectItemInfoListVw.do")
    public String selectItemInfoListVw() {
        return "/mng/trd/iim/selectItemInfoListVw";
    }

    @RequestMapping("/insertItemInfoBatch.do")
    @ResponseBody
    public ItemInfo insertItemInfoBatch(@RequestBody ItemInfoVO vo) throws JSONException, IOException {
        return itemInfoService.insertItemInfoBatch(vo);
    }
}
