package brew.mng.usr.uex.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface UserExitService {

    public UserExit selectUserExitList(UserExitVO vo);
    public UserExit updateUserExit(UserExitVO vo);
}
