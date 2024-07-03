package brew.cmm.service.sns.service;

import org.json.JSONException;

import java.util.Map;

public interface GoogleService {

    public Map<String, String> getAccessToken(String code) throws JSONException;

    public Map<String, String> getUserInfo(String accessToken) throws JSONException;

    public Login insertGoogleLogin(Map<String, String> userInfo);
    public Login selectUserLoginInfo(LoginVO vo);
}
