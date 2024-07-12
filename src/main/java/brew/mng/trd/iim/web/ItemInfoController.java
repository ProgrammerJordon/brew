package brew.mng.trd.iim.web;

import brew.mng.trd.iim.service.ItemInfo;
import brew.mng.trd.iim.service.ItemInfoService;
import brew.mng.trd.iim.service.ItemInfoVO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/selectItemInfoList.do")
    @ResponseBody
    public ItemInfo selectItemInfoList(@RequestBody ItemInfoVO vo) {
        return itemInfoService.selectItemInfoList(vo);
    }

    @RequestMapping("/insertItemInfoBatch.do")
    @ResponseBody
    public ItemInfo insertItemInfoBatch(@RequestBody ItemInfoVO vo) throws JSONException, IOException {
        return itemInfoService.insertItemInfoBatch(vo);
    }

    @RequestMapping("/selectItemInfoDtlsVw.do")
    public String selectItemInfoDtlsVw(@RequestParam (name = "srtnCd") String srtnCd,
                                       Model model) {
        model.addAttribute("srtnCd", srtnCd);
        return "/mng/trd/iim/selectItemInfoDtlsVw";
    }

    @RequestMapping("/selectItemInfoDtls.do")
    @ResponseBody
    public ItemInfo selectItemInfoDtls(@RequestBody ItemInfoVO vo) throws JSONException, IOException {
        return itemInfoService.selectItemInfoDtls(vo);
    }
}
