package brew.cmm.service.sns.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.KakaoService;
import brew.cmm.service.sns.service.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    private final String KAKAO_REST_API_KEY = BrewProperties.getProperty("kakao.api.properties"); // 여기에 실제 REST API 키를 입력하세요.
    private final String KAKAO_REDIRECT_URI = BrewProperties.getProperty("kakao.callback.url"); // 여기에 실제 리디렉션 URI를 입력하세요.

    @GetMapping("/kakaoOauth.do")
    public String kakaoOauth(RedirectAttributes redirectAttributes) {

        String requestUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?response_type=code" +
                "&client_id=" + KAKAO_REST_API_KEY +
                "&redirect_uri=" + KAKAO_REDIRECT_URI +
                "&prompt=login";
                //"&scope=profile,account_email"
        return "redirect:" + requestUrl;
    }


    @RequestMapping("/kakaoCallback.do")
    public String kakaoCallback(@RequestParam("code") String code, HttpServletRequest request) {
        try {
            // 받은 인증 코드로 토큰 요청
            String tokenUrl = "https://kauth.kakao.com/oauth/token" +
                    "?grant_type=authorization_code" +
                    "&client_id=" + KAKAO_REST_API_KEY +
                    "&redirect_uri=" + KAKAO_REDIRECT_URI +
                    "&code=" + code;

            URL url = new URL(tokenUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                String accessToken = jsonObject.getString("access_token");

                // 액세스 토큰으로 사용자 정보 요청
                JSONObject userInfo = KakaoUserInfo(accessToken);

                JSONObject kakaoAccount = userInfo.getJSONObject("kakao_account");
                JSONObject profile = kakaoAccount.getJSONObject("profile");

                String userId = String.valueOf(userInfo.getLong("id"));
                String nickname = profile.getString("nickname");
                String profileImageUrl = profile.getString("profile_image_url");
                String thumbnailImageUrl = profile.getString("thumbnail_image_url");

                // 컨트롤러에서 확인할 객체 생성
                LoginVO vo = new LoginVO();
                vo.setUserId(userId);
                vo.setNickNm(nickname);
                vo.setProfileImgUrl(profileImageUrl);
                vo.setThumbnailImgUrl(thumbnailImageUrl);

                Login kakao = kakaoService.insertKakaoLogin(vo);

                if(kakao.getLoginVO().getCount() == 1 || kakao.getLoginVO().getResult() == 2) {

                    Login login = kakaoService.selectUserLoginInfo(vo);

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
                    session.setAttribute("accessToken", accessToken);
                }

            } else {
                System.out.println("POST request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/svc/min/idx/index"; // 로그인 후 리디렉션할 경로
    }


    private JSONObject KakaoUserInfo(String accessToken) {
        String requestUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return new JSONObject(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
