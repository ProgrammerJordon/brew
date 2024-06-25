package brew.svc.min.idx.web;

import brew.svc.mem.lgi.service.LoginVO;
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
    public String selectIndexVw(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            model.addAttribute("userId", session.getAttribute("userId"));
            model.addAttribute("userNm", session.getAttribute("userNm"));
            model.addAttribute("nickNm", session.getAttribute("nickNm"));
            model.addAttribute("authCd", session.getAttribute("authCd"));
            model.addAttribute("profileImgUrl", session.getAttribute("profileImgUrl"));
        }
        return "/svc/min/idx/index";
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
