package brew.svc.csc.rev.web;

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
