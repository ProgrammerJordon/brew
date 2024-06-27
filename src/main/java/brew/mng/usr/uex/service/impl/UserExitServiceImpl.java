package brew.mng.usr.uex.service.impl;

import brew.mng.usr.uex.service.UserExit;
import brew.mng.usr.uex.service.UserExitService;
import brew.mng.usr.uex.service.UserExitVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("UserExitService")
@RequiredArgsConstructor
public class UserExitServiceImpl implements UserExitService {

    private final UserExitDAO userExitDAO;

    @Override
    public UserExit selectUserExitList(UserExitVO vo) {
        return UserExit.builder()
                .userExitVOList(userExitDAO.selectUserExitList(vo))
                .build();
    }

    @Transactional
    @Override
    public UserExit updateUserExit(UserExitVO vo) {

        int result = 0;

        List<Map<String, String>> chk = (List<Map<String, String>>) vo.getChk();

        for(int i = 0; i < chk.size(); i++) {
            UserExitVO param = new UserExitVO();
            param.setUserSn(chk.get(i).get("userSn"));
            int count = userExitDAO.updateUserExit(param);
            if(count == 1) {
                result++;
            }
        }

        for(int i =0; i < chk.size(); i++) {
            UserExitVO param = new UserExitVO();
            param.setExitSn(chk.get(i).get("exitSn"));
            param.setUserSn(chk.get(i).get("userSn"));
            int count = userExitDAO.updateUserLoginExit(param);
            if(count == 1) {
                result++;
            }
        }

        if(result == chk.size() * 2) {
            vo.setResultMessage("탈퇴회원을 정상적으로 활성화하였습니다.");
        }else {
            vo.setResultMessage("탈퇴회원 활성화를 실패하였습니다.");
        }
        return UserExit.builder()
                .userExitVO(vo)
                .build();
    }
}
