package brew.mng.usr.uat.service.impl;

import brew.mng.usr.uat.service.UserAuth;
import brew.mng.usr.uat.service.UserAuthService;
import brew.mng.usr.uat.service.UserAuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserAuthService")
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthDAO userAuthDAO;

    @Override
    public UserAuth selectUserAuthList(UserAuthVO vo) {
        return UserAuth.builder()
                .userAuthVOList(userAuthDAO.selectUserAuthList(vo))
                .build();
    }

    @Override
    public UserAuth selectUserAuthDtls(UserAuthVO vo) {
        return UserAuth.builder()
                .userAuthVO(userAuthDAO.selectUserAuthDtls(vo))
                .build();
    }

    @Override
    public UserAuth updateUserAuth(UserAuthVO vo) {
        int result = userAuthDAO.updateUserAuth(vo);
        if(result == 1) {
            vo.setResultMessage("회원권한이 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("회원권한 수정에 실패하였습니다.");
        }
        return UserAuth.builder()
                .userAuthVO(vo)
                .build();
    }
}
