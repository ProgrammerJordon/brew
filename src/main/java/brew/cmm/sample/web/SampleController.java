package brew.cmm.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

    @RequestMapping("/index.do")
    public String index() {
        return "/index";
    }

    @RequestMapping("/sr_board")
    public String sr_board() {
        return "/sr_board/sr_board";
    }
}
