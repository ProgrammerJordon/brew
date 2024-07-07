package brew.mng.cmm.faq.web;

import brew.mng.cmm.faq.service.Faq;
import brew.mng.cmm.faq.service.FaqService;
import brew.mng.cmm.faq.service.FaqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/cmm/faq")
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @RequestMapping("/selectFaqListVw.do")
    public String selectFaqListVw() {
        return "/mng/cmm/faq/selectFaqListVw";
    }

    @RequestMapping("/selectFaqList.do")
    @ResponseBody
    public Faq selectFaqList(@RequestBody FaqVO vo) {
        return faqService.selectFaqList(vo);
    }


    @RequestMapping("/insertFaqInsertVw.do")
    public String insertFaqInsertVw() {
        return "/mng/cmm/faq/insertFaqInsertVw";
    }

    @RequestMapping("/insertFaq.do")
    @ResponseBody
    public Faq insertFaq(@RequestBody FaqVO vo) {
        return faqService.insertFaq(vo);
    }

    @RequestMapping("/selectFaqDtlsVw.do")
    public String selectFaqDtlsVw(@RequestParam(name = "faqId", required = false) String faqId,
                                  Model model) {
        model.addAttribute("faqId", faqId);
        return "/mng/cmm/faq/selectFaqDtlsVw";
    }

    @RequestMapping("/selectFaqDtls.do")
    @ResponseBody
    public Faq selectFaqDtls(@RequestBody FaqVO vo) {
        return faqService.selectFaqDtls(vo);
    }

    @RequestMapping("/updateFaq.do")
    @ResponseBody
    public Faq updateFaq(@RequestBody FaqVO vo) {
        return faqService.updateFaq(vo);
    }

    @RequestMapping("/deleteFaq.do")
    @ResponseBody
    public Faq deleteFaq(@RequestBody FaqVO vo) {
        return faqService.deleteFaq(vo);
    }
}