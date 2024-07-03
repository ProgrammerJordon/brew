package brew.cmm.service.sns.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface KakaoService {

    public Login insertKakaoLogin(LoginVO vo);

    public Login selectUserLoginInfo(LoginVO vo);
}
