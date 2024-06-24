package brew.svc.mem.lgi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/svc/mem/lgi")
@RequiredArgsConstructor
public class LoginController {

    @Value("${kakao.js.properties}")
    private String kakaoJsProperties;

    @RequestMapping("/selectLoginVw.do")
    public String selectLoginVw(Model model) {
        model.addAttribute("kakaoJsProperties", kakaoJsProperties);
        return "/svc/mem/lgi/selectLoginVw";
    }
}
