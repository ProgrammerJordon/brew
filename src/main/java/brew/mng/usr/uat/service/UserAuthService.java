package brew.mng.usr.uat.service;

public interface UserAuthService {

    public UserAuth selectUserAuthList(UserAuthVO vo);
    public UserAuth selectUserAuthDtls(UserAuthVO vo);
    public UserAuth updateUserAuth(UserAuthVO vo);
}
