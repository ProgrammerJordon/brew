package brew.mng.usr.usi.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface UserSignInService {

    public UserSignIn selectUserSignInList(UserSignInVO vo);
}
