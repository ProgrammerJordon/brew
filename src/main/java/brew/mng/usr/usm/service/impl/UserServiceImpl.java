package brew.mng.usr.usm.service.impl;

import brew.cmm.util.BrewMessageUtil;
import brew.mng.usr.usm.service.User;
import brew.mng.usr.usm.service.UserService;
import brew.mng.usr.usm.service.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User selectUserMngList(UserVO vo) {
        return User.builder()
                .userVOList(userDAO.selectUserMngList(vo))
                .build();
    }

    @Override
    public User selectUserMngDtls(UserVO vo) {
        return User.builder()
                .userVO(userDAO.selectUserMngDtls(vo))
                .build();
    }

    @Override
    public User updateUserMng(UserVO vo) {
        int result = userDAO.updateUserMng(vo);
        if(result == 1) {
            vo.setResultMessage("회원정보 수정이 정상적으로 진행되었습니다.");
        }else {
            vo.setResultMessage("회원정보 수정을 실패하였습니다.");
        }
        return User.builder()
                .userVO(vo)
                .build();
    }

    @Transactional
    @Override
    public User exitUserMng(UserVO vo) {
        int rs1 = userDAO.updateExitUserMng(vo);
        UserVO param = userDAO.selectUserMngDtls(vo);
        int rs2 = userDAO.insertExitUserMng(param);
        if(rs1 + rs2 == 2) {
            vo.setResultMessage("정상적으로 회원탈퇴 되었습니다.");
        }else {
            vo.setResultMessage("회원탈퇴에 실패하였습니다.");
        }
        return User.builder()
                .userVO(vo)
                .build();
    }
}
