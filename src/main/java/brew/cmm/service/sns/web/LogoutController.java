package brew.cmm.service.sns.web;

import brew.cmm.service.ppt.BrewProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LogoutController {

    @RequestMapping("/logout.do")
    public String snsLogout(HttpServletRequest request) {

        HttpSession session =  request.getSession();

        String loginSe = String.valueOf(session.getAttribute("loginSe"));

        if(loginSe != null && loginSe.equals("KAKAO")) {

            String accessToken = String.valueOf(session.getAttribute("accessToken"));
            String kakaoLogoutUrl = "https://kapi.kakao.com/v1/user/logout";

            // Spring 프레임워크에서 제공하는 HTTP 통신
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(kakaoLogoutUrl + "?access_token=" + accessToken, null, String.class);

            // 카카오ID 반환
            System.out.println(response);

        }else if (loginSe != null && loginSe.equals("GOOGLE")) {

            // 구글은 access_token 안에 토큰값이 String이 아닌 Map<String, Object> 상태
            String accessToken = String.valueOf(session.getAttribute("accessToken"));

            String googleLogoutUrl = "https://oauth2.googleapis.com/revoke";


            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("token", accessToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(googleLogoutUrl, requestEntity, String.class);

        }else if (loginSe != null && loginSe.equals("NAVER")) {

            String NAVER_CLIENT_ID = BrewProperties.getProperty("naver.clientId.properties");
            String NAVER_PASSWORD_KEY = BrewProperties.getProperty("naver.clientPassword.properties");


            String accessToken = String.valueOf(session.getAttribute("accessToken"));

            try {
                String apiURL = "https://nid.naver.com/oauth2.0/token";
                String params = "grant_type=delete&client_id=" + NAVER_CLIENT_ID + "&client_secret=" + NAVER_PASSWORD_KEY + "&access_token=" + accessToken + "&service_provider=NAVER";

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                os.write(params.getBytes());
                os.flush();
                os.close();

                int responseCode = con.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                if (responseCode == 200) {
                    System.out.println("Successfully logged out from Naver");
                } else {
                    System.out.println("Failed to log out from Naver");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        logout(request);

        return "redirect:/index.do"; // 로그아웃 후 홈 페이지로 리다이렉트
    }

    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 존재하지 않으면 null 반환

        if (session != null) {

            session.removeAttribute("userSn");
            session.removeAttribute("userId");
            session.removeAttribute("userNm");
            session.removeAttribute("nickNm");
            session.removeAttribute("email");
            session.removeAttribute("authCd");
            session.removeAttribute("loginSe");
            session.removeAttribute("profileImgUrl");
            session.removeAttribute("thumbnailImgUrl");
            session.removeAttribute("accessToken");
            session.setAttribute("visitor", false);

        }
    }

}
