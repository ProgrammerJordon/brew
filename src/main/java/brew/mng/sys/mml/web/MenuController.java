package brew.mng.sys.mml.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/sys/mml")
@RequiredArgsConstructor
public class MenuController {

    @RequestMapping("/selectMenuListVw.do")
    public String selectMenuListVw() {
        return "/mng/sys/mml/selectMenuListVw";
    }
}
