package brew.cmm.service.sns.service.impl;

import brew.cmm.service.sns.service.LoginVO;
import brew.cmm.service.sns.service.Login;
import brew.cmm.service.sns.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("KakaoService")
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final LoginDAO loginDAO;

    @Transactional
    @Override
    public Login insertKakaoLogin(LoginVO vo) {
        int count = loginDAO.selectSignInYn(vo);
        vo.setCount(count);

        int result = 0;

        if(count == 1) {
            vo.setResultMessage("정상적으로 로그인되었습니다.");
        }else {
            int sql1 = loginDAO.insertKakaoLogin(vo);

            if(sql1 == 1) {
                result++;

                int sql2 = loginDAO.insertKakaoSignIn(vo);

                if(sql2 == 1) {result++;}
            }
            vo.setResult(result);
            if(result == 2) {
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
    public Login selectUserLoginInfo(LoginVO vo) {
        return Login.builder()
                .loginVO(loginDAO.selectUserLoginInfo(vo))
                .build();
    }
}
