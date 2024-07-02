package brew.svc.min.idx.web;

import brew.cmm.service.ppt.BrewProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @RequestMapping("/")
    public String indexVw() {
        return "redirect:/index.do";
    }

    @RequestMapping("/index.do")
    public String selectIndexVw(Model model, HttpServletRequest request) {

        model.addAttribute("kakaoJsProperties", BrewProperties.getProperty("kakao.js.properties"));

        HttpSession session = request.getSession(false);
        if (session != null) {
            model.addAttribute("userSn", session.getAttribute("userSn"));
            model.addAttribute("userId", session.getAttribute("userId"));
            model.addAttribute("userNm", session.getAttribute("userNm"));
            model.addAttribute("nickNm", session.getAttribute("nickNm"));
            model.addAttribute("authCd", session.getAttribute("authCd"));
            model.addAttribute("profileImgUrl", session.getAttribute("profileImgUrl"));
        }
        return "/svc/min/idx/index";
    }

}
