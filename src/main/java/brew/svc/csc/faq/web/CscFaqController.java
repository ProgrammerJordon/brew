package brew.svc.csc.faq.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/svc/csc/faq")
@RequiredArgsConstructor
public class CscFaqController {

    @RequestMapping("/selectFaqListVw.do")
    public String selectFaqListVw() {

        return "/svc/csc/faq/selectFaqListVw";
    }
}
