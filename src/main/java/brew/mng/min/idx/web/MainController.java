package brew.mng.min.idx.web;

import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.BoardVO;
import brew.mng.cmm.faq.service.Faq;
import brew.mng.cmm.faq.service.FaqService;
import brew.mng.cmm.faq.service.FaqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/min/idx")
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;
    private final FaqService faqService;

    @RequestMapping("/selectMainVw.do")
    public String selectMainVw() {
        return "/mng/min/idx/MainVw";
    }

    @RequestMapping("/selectBoardList.do")
    @ResponseBody
    public Board selectBoardList(@RequestBody BoardVO vo) {
        return boardService.selectBoardList(vo);
    }

    @RequestMapping("/selectFaqList.do")
    @ResponseBody
    public Faq selectFaqList(@RequestBody FaqVO vo) {
        return faqService.selectFaqList(vo);
    }
}
