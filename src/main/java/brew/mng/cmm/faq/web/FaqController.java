package brew.mng.cmm.faq.web;

import brew.mng.cmm.faq.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/cmm/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @RequestMapping("/selectFaqListVw.do")
    public String selectFaqListVw() {
        return "/mng/cmm/faq/selectFaqListVw";
    }
}
