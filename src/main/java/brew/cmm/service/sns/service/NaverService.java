package brew.cmm.service.sns.service;

import org.json.JSONException;

import java.util.Map;

public interface NaverService {

    public Map<String, String> getAccessToken(String code, String state) throws JSONException;

    Map<String, String> getUserInfo(String accessToken) throws JSONException;

    Login insertNaverLogin(Map<String, String> userInfo);
}
