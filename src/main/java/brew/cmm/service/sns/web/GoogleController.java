package brew.cmm.service.sns.web;

import brew.cmm.service.sns.service.GoogleService;
import brew.cmm.service.sns.service.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GoogleController {

    private final GoogleService googleService;

    @RequestMapping("/googleOauth.do")
    public String googleOauth(@RequestParam("code") String code, HttpServletRequest request) {
        try {
            // 엑세스 토큰 발급
            Map<String, String> accessToken = googleService.getAccessToken(code);
            // 엑세스 토큰으로 유저정보 조회
            Map<String, String> userInfo = googleService.getUserInfo(accessToken.get("access_token"));
            // 유저정보로 회원 등록여부 판단 및 회원가입
            Login google = googleService.insertGoogleLogin(userInfo);


            // 세션 생성 회원가입이 되어 있거나 회원가입이 완료된 경우 실행
            if(google.getLoginVO().getCount() == 1 || google.getLoginVO().getResult() == 2) {

                Login login = googleService.selectUserLoginInfo(google.getLoginVO());

                HttpSession checkSession = request.getSession(false);

                if(checkSession == null) {
                    // 이유는 모르겠으나 세션이 한번에 안만들어져서 추가함.
                    HttpSession session = request.getSession();
                }

                HttpSession session = request.getSession(true);

                session.setAttribute("userSn", login.getLoginVO().getUserSn());
                session.setAttribute("userId", login.getLoginVO().getUserId());
                session.setAttribute("userNm", login.getLoginVO().getUserNm());
                session.setAttribute("nickNm", login.getLoginVO().getNickNm());
                session.setAttribute("email", login.getLoginVO().getEmail());
                session.setAttribute("authCd", login.getLoginVO().getAuthCd());
                session.setAttribute("loginSe", login.getLoginVO().getLoginSe());
                session.setAttribute("profileImgUrl", login.getLoginVO().getProfileImgUrl());
                session.setAttribute("thumbnailImgUrl", login.getLoginVO().getThumbnailImgUrl());
                session.setAttribute("accessToken", accessToken.get("access_token"));
            }

            return "/svc/min/idx/index";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/error";

        }
    }
}
