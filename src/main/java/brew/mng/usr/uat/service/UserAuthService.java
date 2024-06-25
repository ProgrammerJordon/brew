package brew.mng.usr.uat.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface UserAuthService {

    public UserAuth selectUserAuthList(UserAuthVO vo);
}
