package brew.svc.mem.lgi.service.impl;

import brew.svc.mem.lgi.service.LoginVO;
import brew.svc.mem.lgi.service.Login;
import brew.svc.mem.lgi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("LoginService")
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginDAO loginDAO;

    @Override
    public Login insertKakaoLogin(LoginVO vo) {
        int count = loginDAO.selectSignInYn(vo);
        vo.setCount(count);
        if(count == 1) {
            vo.setResultMessage("정상적으로 로그인되었습니다.");
        }else {
            int result = loginDAO.insertKakaoLogin(vo);
            vo.setResult(result);
            if(result == 1) {
                vo.setResultMessage("정상적으로 회원가입에 성공하였습니다.");
            }else {
                vo.setResultMessage("로그인에 실패하였습니다.");
            }
        }
        return Login.builder()
                .loginVO(vo)
                .build();
    }

    @Override
    public Login selectKakaoLogin(LoginVO vo) {
        return Login.builder()
                .loginVO(loginDAO.selectKakaoLogin(vo))
                .build();
    }
}
