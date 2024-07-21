package brew.svc.csc.not.web;

import brew.svc.csc.not.service.Notice;
import brew.svc.csc.not.service.NoticeService;
import brew.svc.csc.not.service.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/svc/csc/not")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @RequestMapping("selectNoticeListVw.do")
    public String selectNoticeListVw() {
        return "/svc/csc/not/selectNoticeListVw";
    }

    @RequestMapping("/selectNoticeList.do")
    @ResponseBody
    public Notice selectNoticeList(@RequestBody NoticeVO vo) {
        return noticeService.selectNoticeList(vo);
    }

    @RequestMapping("/selectNoticeInsertVw.do")
    public String selectNoticeInsertVw() {
        return "/svc/csc/not/selectNoticeInsertVw";
    }



}
