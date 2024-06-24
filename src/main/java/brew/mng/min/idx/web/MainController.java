package brew.mng.min.idx.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/min/idx")
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/selectMainVw.do")
    public String selectMainVw() {
        return "/mng/min/idx/MainVw";
    }
}
