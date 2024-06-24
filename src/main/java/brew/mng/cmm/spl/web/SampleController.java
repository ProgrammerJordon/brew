package brew.mng.cmm.spl.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/cmm/spl")
@RequiredArgsConstructor
public class SampleController {

    @RequestMapping("/selectSampleListVw.do")
    public String selectSampleListVw() {
        return "/mng/cmm/spl/selectSampleListVw";
    }
}
