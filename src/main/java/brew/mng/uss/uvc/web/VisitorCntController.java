package brew.mng.uss.uvc.web;

import brew.mng.uss.uvc.service.VisitorCnt;
import brew.mng.uss.uvc.service.VisitorCntService;
import brew.mng.uss.uvc.service.VisitorCntVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/uss/uvc")
@RequiredArgsConstructor
public class VisitorCntController {

    private final VisitorCntService visitorCntService;

    @RequestMapping("/selectVisitorCntVw.do")
    public String selectVisitorCntVw() {
        return "/mng/uss/uvc/selectVisitorCntVw";
    }

    @RequestMapping("/selectVisitorCntList.do")
    @ResponseBody
    public VisitorCnt selectVisitorCntList(@RequestBody VisitorCntVO vo) {
        return visitorCntService.selectVisitorCntList(vo);
    }

    @RequestMapping("/selectVisitorCnt.do")
    @ResponseBody
    public VisitorCnt selectVisitorCnt(@RequestBody VisitorCntVO vo) {
        return visitorCntService.selectVisitorCnt(vo);
    }
}
