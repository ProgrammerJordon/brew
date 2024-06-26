package brew.mng.usr.usm.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    public User selectUserMngList(UserVO vo);
    public User selectUserMngDtls(UserVO vo);
    public User updateUserMng(UserVO vo);
    public User exitUserMng(UserVO vo);
}
