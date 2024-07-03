package brew.cmm.service.sns.service.impl;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.service.sns.service.GoogleService;
import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.LoginVO;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service("GoogleService")
@RequiredArgsConstructor
public class GoogleServiceImpl implements GoogleService {

    private final LoginDAO loginDAO;

    private final String GOOGLE_CLIENT_ID = BrewProperties.getProperty("google.clientId.properties");
    private final String GOOGLE_PASSWORD_KEY = BrewProperties.getProperty("google.clientPassword.properties");
    private final String GOOGLE_REDIRECT_URI = BrewProperties.getProperty("google.redirect.url");

    public Map<String, String> getAccessToken(String code) throws JSONException {
        String accessTokenUrl = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", GOOGLE_CLIENT_ID);
        requestBody.add("client_secret", GOOGLE_PASSWORD_KEY);
        requestBody.add("redirect_uri", GOOGLE_REDIRECT_URI);
        requestBody.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            String responseBody = responseEntity.getBody();

            JSONObject jsonResponse = new JSONObject(responseBody);

            // 결과를 담을 Map 생성
            Map<String, String> responseMap = new HashMap<>();

            // JSONObject에서 필요한 값들을 추출하여 Map에 저장
            responseMap.put("access_token", jsonResponse.getString("access_token"));
            responseMap.put("id_token", jsonResponse.getString("id_token"));
            responseMap.put("scope", jsonResponse.getString("scope"));
            responseMap.put("token_type", jsonResponse.getString("token_type"));
            responseMap.put("expires_in", jsonResponse.getString("expires_in"));

            return responseMap;

        } else {
            throw new RuntimeException("Failed to get access token: " + responseEntity.getStatusCodeValue());
        }
    }

    @Override
    public Map<String, String> getUserInfo(String accessToken) throws JSONException {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String responseBody = responseEntity.getBody();

            // JSON 문자열을 JSONObject로 변환
            JSONObject jsonResponse = new JSONObject(responseBody);

            // 필요한 사용자 정보를 Map에 저장
            Map<String, String> userInfoMap = new HashMap<>();
            userInfoMap.put("userId", jsonResponse.getString("sub")); // 사용자 ID
            userInfoMap.put("email", jsonResponse.getString("email")); // 이메일
            userInfoMap.put("nickNm", jsonResponse.getString("name")); // 닉네임
            userInfoMap.put("givenNm", jsonResponse.getString("given_name")); // 이름
            userInfoMap.put("familyNm", jsonResponse.getString("family_name")); // 성
            userInfoMap.put("profileImgUrl", jsonResponse.getString("picture")); // 프로필 사진 URL 등
            userInfoMap.put("emailVerified", jsonResponse.getString("email_verified")); // 이메일인증 여부

            return userInfoMap;

        } else {
            throw new RuntimeException("Failed to get user info: " + responseEntity.getStatusCodeValue());
        }
    }

    @Override
    public Login insertGoogleLogin(Map<String, String> userInfo) {
        LoginVO vo = new LoginVO();

        vo.setUserId(userInfo.get("userId"));
        vo.setEmail(userInfo.get("email"));
        vo.setNickNm(userInfo.get("nickNm"));
        vo.setGivenNm(userInfo.get("givenNm"));
        vo.setFamilyNm(userInfo.get("familyNm"));
        vo.setProfileImgUrl(userInfo.get("profileImgUrl"));
        vo.setEmailVerified(userInfo.get("emailVerified"));

        int count = loginDAO.selectSignInYn(vo);

        int result = 0;

        if(count == 1) {

            vo.setResultMessage("정상적으로 로그인 되었습니다.");
            vo.setCount(count);

        }else {

            int sql1 = loginDAO.insertGoogleLogin(vo);

            if(sql1 == 1) {
                result++;

                int sql2 = loginDAO.insertGoogleSignIn(vo);

                if(sql2 == 1) {result++;}
            }

            vo.setResult(result);

            if(result == 2) {
                vo.setResultMessage("정상적으로 회원가입에 성공하였습니다.");
            }else {
                vo.setResultMessage("로그인에 실패하였습니다.");
            }

        }


        return Login.builder()
                .loginVO(vo)
                .build();
    }

    // 유저정보 조회
    @Override
    public Login selectUserLoginInfo(LoginVO vo) {
        return Login.builder()
                .loginVO(loginDAO.selectUserLoginInfo(vo))
                .build();
    }


}
