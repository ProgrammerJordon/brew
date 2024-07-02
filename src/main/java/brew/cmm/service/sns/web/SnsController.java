package brew.cmm.service.sns.web;

import brew.cmm.service.ppt.BrewProperties;
import brew.svc.mem.lgi.service.Login;
import brew.svc.mem.lgi.service.LoginService;
import brew.svc.mem.lgi.service.LoginVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequiredArgsConstructor
public class SnsController {

    private final LoginService loginService;

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

                Login kakao = loginService.insertKakaoLogin(vo);

                if(kakao.getLoginVO().getCount() == 1 || kakao.getLoginVO().getResult() == 2) {

                    Login login = loginService.selectKakaoLogin(vo);

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

    // 구글

    private final String GOOGLE_API_KEY = BrewProperties.getProperty("google.api.properties");
    private final String GOOGLE_CLIENT_ID = BrewProperties.getProperty("google.clientId.properties");
    private final String GOOGLE_PASSWORD_KEY = BrewProperties.getProperty("google.clientPassword.properties");
    private final String GOOGLE_REDIRECT_URI = BrewProperties.getProperty("google.redirect.url");
    private final String GOOGLE_CALLBACK_URI = BrewProperties.getProperty("google.callback.url");

//    @RequestMapping("/googleOauth.do")
//    @ResponseBody
//    public String googleOauth(@RequestParam("code") String code) throws JSONException {
//        String accessTokenUrl = "https://oauth2.googleapis.com/token";
//
//        // POST 요청을 보내기 위한 RestTemplate 객체 생성
//        RestTemplate restTemplate = new RestTemplate();
//
//        // POST 요청을 위한 파라미터 설정
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("code", code);
//        requestBody.add("client_id", GOOGLE_CLIENT_ID);
//        requestBody.add("client_secret", GOOGLE_PASSWORD_KEY);
//        requestBody.add("redirect_uri", GOOGLE_CALLBACK_URI);
//        requestBody.add("grant_type", "authorization_code");
//
//        // 액세스 토큰 요청 및 응답 받기
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(accessTokenUrl, requestBody, String.class);
//
//        // 응답에서 액세스 토큰 추출
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            String responseBody = responseEntity.getBody();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            String accessToken = jsonResponse.getString("access_token");
//
//            // 여기서 액세스 토큰을 사용하여 추가 작업을 수행할 수 있음
//            // 예를 들어, 구글 API 호출이나 사용자 정보 가져오기 등
//            // 여기서는 단순히 응답을 반환하거나 JSON으로 파싱하여 필요한 작업을 수행할 수 있음
//            return responseBody;
//        } else {
//            // 오류 처리
//            return "Error occurred: " + responseEntity.getStatusCodeValue();
//        }
//    }

//    @GetMapping("/googleCallback.do")
//    @ResponseBody
//    public String getUserInfo(@RequestParam("accessToken") String accessToken) {
//        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            return responseEntity.getBody();
//        } else {
//            return "Error occurred: " + responseEntity.getStatusCodeValue();
//        }
//    }


    @RequestMapping("/logout.do")
    public String snsLogout(HttpServletRequest request) {

        HttpSession session =  request.getSession();

        String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
        String accessToken = (String) session.getAttribute("accessToken");

        String loginSe = (String) session.getAttribute("loginSe");

        if(loginSe.equals("KAKAO")) {

            // Spring 프레임워크에서 제공하는 HTTP 통신
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(logoutUrl + "?access_token=" + accessToken, null, String.class);

            // 카카오ID 반환
            System.out.println(response);

        }

        logout(request);

        return "redirect:/index.do"; // 로그아웃 후 홈 페이지로 리다이렉트
    }

    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 null 반환

        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
    }
}
