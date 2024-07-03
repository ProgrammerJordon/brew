package brew.svc.mem.lgi.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.service.sns.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/svc/mem/lgi")
@RequiredArgsConstructor
public class LoginController {

    private final KakaoService kakaoService;

    @RequestMapping("/selectLoginVw.do")
    public String selectLoginVw(Model model) {

        // kakao
        model.addAttribute("kakaoKey", BrewProperties.getProperty("kakao.js.properties"));
        model.addAttribute("kakaoRedirectUrl", BrewProperties.getProperty("kakao.redirect.url"));
        // google
        model.addAttribute("googleKey", BrewProperties.getProperty("google.api.properties"));
        model.addAttribute("googleRedirectUrl", BrewProperties.getProperty("google.redirect.url"));
        model.addAttribute("googleClientId", BrewProperties.getProperty("google.clientId.properties"));
        model.addAttribute("googleClientPassword", BrewProperties.getProperty("google.clientPassword.properties"));

        return "/svc/mem/lgi/selectLoginVw";
    }

}
