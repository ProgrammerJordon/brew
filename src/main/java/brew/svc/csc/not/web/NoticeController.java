package brew.svc.csc.not.web;

import brew.svc.csc.not.service.Notice;
import brew.svc.csc.not.service.NoticeService;
import brew.svc.csc.not.service.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("insertNotice.do")
    @ResponseBody
    public Notice insertNotice(@RequestBody NoticeVO vo) {
        return noticeService.insertNotice(vo);
    }

    @RequestMapping("selectNoticeDtlsVw.do")
    public String selectNoticeDtlsVw(@RequestParam(name = "sn") int sn,
                                     NoticeVO vo,
                                     Model model) {
        vo.setSn(sn);
        model.addAttribute("vo", vo);

        return "/svc/csc/not/selectNoticeDtlsVw";
    }

    @RequestMapping("selectNoticeDtVw.do")
    @ResponseBody
    public Notice selectNoticeDtVw(@RequestBody NoticeVO vo) {
        return noticeService.selectNoticeDtVw(vo);
    }

    //공지사항 삭제
    @RequestMapping("deleteNotice.do")
    @ResponseBody
    public Notice deleteNotice(@RequestBody NoticeVO vo) {
        return noticeService.deleteNotice(vo);
    }

    //공지사항 수정하는 화면
    @RequestMapping("updateNoticeVw.do")
    public String updateNoticeVw(@RequestParam(name = "sn") int sn,
                                 NoticeVO vo,
                                 Model model) {
//        System.out.println(sn);
        vo.setSn(sn);
        model.addAttribute("vo", vo);

        return "/svc/csc/not/updateNoticeVw";
    }

    //공지사항 수정
    @RequestMapping("updateNotice.do")
    @ResponseBody
    public Notice updateNotice(@RequestBody NoticeVO vo) {
        return noticeService.updateNotice(vo);
    }

}
