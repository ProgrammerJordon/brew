package brew.cmm.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

    @RequestMapping("/index.do")
    public String index() {
        return "/index";
    }
}
