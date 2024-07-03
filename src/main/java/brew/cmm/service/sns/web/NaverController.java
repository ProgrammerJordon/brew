package brew.cmm.service.sns.web;

import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.NaverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NaverController {

    private final NaverService naverService;

    @RequestMapping("/naverOauth.do")
    public String naverOauth(
            @RequestParam String code
                            , @RequestParam String state
                            , HttpServletRequest request
            ) throws JSONException {
        try {
            // 네이버 토근발급
            Map<String, String> token = naverService.getAccessToken(code, state);
            // 네이버 프로필 조회
            Map<String, String> userInfo = naverService.getUserInfo(token.get("access_token"));
            // 네이버 로그인 및 회원가입
            Login naver = naverService.insertNaverLogin(userInfo);
            System.out.println(naver);

            if(naver.getLoginVO().getCount() == 1 || naver.getLoginVO().getResult() == 2) {
                // 세션만들공간
                Login login = naverService.selectUserLoginInfo(naver.getLoginVO());

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
                session.setAttribute("accessToken", token.get("access_token"));
            }

            return "/svc/min/idx/index";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/error";

        }
    }
}
