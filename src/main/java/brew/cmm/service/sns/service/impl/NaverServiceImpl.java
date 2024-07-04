package brew.cmm.service.sns.service.impl;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.LoginVO;
import brew.cmm.service.sns.service.NaverService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service("NaverService")
@RequiredArgsConstructor
public class NaverServiceImpl implements NaverService {

    private final LoginDAO loginDAO;

    private final String NAVER_CLIENT_ID = BrewProperties.getProperty("naver.clientId.properties");
    private final String NAVER_PASSWORD_KEY = BrewProperties.getProperty("naver.clientPassword.properties");
    private final String NAVER_CALLBACK_URI = BrewProperties.getProperty("naver.callback.url");
    private final String NAVER_REDIRECT_URI = BrewProperties.getProperty("naver.redirect.url");


    @Override
    public Map<String, String> getAccessToken(String code, String state) throws JSONException {
        String tokenUrl = "https://nid.naver.com/oauth2.0/token" +
                "?grant_type=authorization_code"
                + "&client_id=" + NAVER_CLIENT_ID
                + "&client_secret=" + NAVER_PASSWORD_KEY
                + "&code=" + code
                + "&state=" + state;

        try {
            URL url = new URL(tokenUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();

            // JSON 파싱
            JSONObject json = new JSONObject(content.toString());
            String accessToken = json.getString("access_token");
            String refreshToken = json.getString("refresh_token");
            String tokenType = json.getString("token_type");
            String expiresIn = json.getString("expires_in");

            Map<String, String> acessToken = new HashMap<>();
            acessToken.put("access_token", accessToken);
            acessToken.put("refresh_token", refreshToken);
            acessToken.put("token_type", tokenType);
            acessToken.put("expires_in", expiresIn);

            return acessToken;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get access token", e);
        }
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map responseBody = response.getBody();
        JSONObject res = new JSONObject(responseBody);
        JSONObject userInfo = res.getJSONObject("response");

        Map<String, String> userInfoMap = new HashMap<>();
        userInfoMap.put("userId", userInfo.getString("id"));
        userInfoMap.put("userNm", userInfo.getString("name"));
        userInfoMap.put("nickNm", userInfo.getString("nickname"));
        userInfoMap.put("profileImgUrl", userInfo.getString("profile_image"));
        userInfoMap.put("email", userInfo.getString("email"));
        userInfoMap.put("birthYear", userInfo.getString("birthyear"));
        userInfoMap.put("birthDay", userInfo.getString("birthday"));
        userInfoMap.put("mobile", userInfo.getString("mobile"));
        userInfoMap.put("mobileE", userInfo.getString("mobile_e164"));
        userInfoMap.put("age", userInfo.getString("age"));
        userInfoMap.put("gender", userInfo.getString("gender"));

        return userInfoMap;
    }

    @Transactional
    @Override
    public Login insertNaverLogin(Map<String, String> userInfo) {
        // 로그인시 회원가입 또는 로그인처리할 코드
        LoginVO vo = new LoginVO();
        vo.setUserId(userInfo.get("userId"));
        vo.setUserNm(userInfo.get("userNm"));
        vo.setNickNm(userInfo.get("nickNm"));
        vo.setProfileImgUrl(userInfo.get("profileImgUrl"));
        vo.setEmail(userInfo.get("email"));
        vo.setMobile(userInfo.get("mobile"));
        vo.setMobileE(userInfo.get("mobileE"));
        vo.setGender(userInfo.get("gender"));
        vo.setAge(userInfo.get("age"));
        vo.setBirthYear(userInfo.get("birthYear"));
        vo.setBirthDay(userInfo.get("birthDay"));

        if(vo.getUserId() != null && !"".equals(vo.getUserId())) {
            int count = loginDAO.selectSignInYn(vo);
            int result = 0;
            if(count == 1) {
                vo.setResultMessage("정상적으로 로그인 되었습니다.");
                vo.setCount(count);
            }else {
                int sql1 = loginDAO.insertNaverLogin(vo);
                if(sql1 == 1) {
                    result++;
                    int sql2 = loginDAO.insertNaverSignIn(vo);
                    if(sql2 == 1) {
                        result++;
                    }
                }
                vo.setResult(result);

                if(result == 2) {
                    vo.setResultMessage("정상적으로 회원가입에 성공하였습니다.");
                }else {
                    vo.setResultMessage("로그인에 실패하였습니다.");
                }
            }
        }
        return Login.builder()
                .loginVO(vo)
                .build();
    }

    @Override
    public Login selectUserLoginInfo(LoginVO vo) {
        return Login.builder()
                .loginVO(loginDAO.selectUserLoginInfo(vo))
                .build();
    }
}
