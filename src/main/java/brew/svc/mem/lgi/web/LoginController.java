package brew.svc.mem.lgi.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.svc.mem.lgi.service.LoginVO;
import brew.svc.mem.lgi.service.Login;
import brew.svc.mem.lgi.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/svc/mem/lgi")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @RequestMapping("/selectLoginVw.do")
    public String selectLoginVw(Model model) {

        model.addAttribute("kakaoKey", BrewProperties.getProperty("kakao.js.properties"));
        model.addAttribute("kakaoDirecturl", BrewProperties.getProperty("kakao.redirect.url"));

        return "/svc/mem/lgi/selectLoginVw";
    }

}
