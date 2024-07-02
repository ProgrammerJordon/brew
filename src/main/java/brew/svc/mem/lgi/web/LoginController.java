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
        model.addAttribute("kakaoJsProperties", BrewProperties.getProperty("kakao.js.properties"));
        return "/svc/mem/lgi/selectLoginVw";
    }

    @RequestMapping("/insertKakaoLogin.do")
    @ResponseBody
    public Login insertKakaoLogin(@RequestBody LoginVO vo) {
        return loginService.insertKakaoLogin(vo);
    }

    @RequestMapping("/selectKakaoLogin.do")
    @ResponseBody
    public Login selectKakaoLogin(@RequestBody LoginVO vo, HttpServletRequest request) {
        Login login = loginService.selectKakaoLogin(vo);
        HttpSession session = request.getSession(false);
        session.setAttribute("userSn", login.getLoginVO().getUserSn());
        session.setAttribute("userId", login.getLoginVO().getUserId());
        session.setAttribute("userNm", login.getLoginVO().getUserNm());
        session.setAttribute("nickNm", login.getLoginVO().getNickNm());
        session.setAttribute("authCd", login.getLoginVO().getAuthCd());
        session.setAttribute("loginSe", login.getLoginVO().getLoginSe());
        session.setAttribute("profileImgUrl", login.getLoginVO().getProfileImgUrl());
        session.setAttribute("thumbnailImgUrl", login.getLoginVO().getThumbnailImgUrl());
        return login;
    }
}
