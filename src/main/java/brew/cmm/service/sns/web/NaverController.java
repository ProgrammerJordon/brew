package brew.cmm.service.sns.web;

import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.NaverService;
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
                            ,@RequestParam String state) throws JSONException {
        try {
            // 네이버 토근발급
            Map<String, String> token = naverService.getAccessToken(code, state);
            // 네이버 프로필 조회
            Map<String, String> userInfo = naverService.getUserInfo(token.get("access_token"));
            // 네이버 로그인 및 회원가입
            Login naver = naverService.insertNaverLogin(userInfo);
            System.out.println(naver);

            if(true) {
                // 세션만들공간
            }

            return "/svc/min/idx/index";

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/error";

        }
    }
}
