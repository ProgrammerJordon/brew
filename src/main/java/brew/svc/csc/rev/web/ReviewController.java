package brew.svc.csc.rev.web;

import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.impl.BoardServiceImpl;
import brew.svc.csc.rev.service.Review;
import brew.svc.csc.rev.service.ReviewService;
import brew.svc.csc.rev.service.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/svc/csc/rev")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final BoardServiceImpl boardService;

    @RequestMapping("selectReviewListVw.do")
    public String selectReviewListVw() {
        return "/svc/csc/rev/selectReviewListVw";
    }

    @RequestMapping("selectReviewList.do")
    @ResponseBody
    public Review selectReviewList(@RequestBody ReviewVO vo) {
        return reviewService.selectReviewList(vo);
    }

}
