package brew.svc.min.idx.web;

import brew.svc.mem.lgi.service.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @Value("${kakao.js.properties}")
    private String kakaoJsProperties;

    @RequestMapping("/index.do")
    public String selectIndexVw(Model model, HttpServletRequest request) {

        model.addAttribute("kakaoJsProperties", kakaoJsProperties);

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

    @RequestMapping("/logout.do")
    public String logout(Model model, HttpServletRequest request) {
        model.addAttribute("kakaoJsProperties", kakaoJsProperties);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/index.do";
    }
}
