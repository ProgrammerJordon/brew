package brew.svc.mem.lgi.service.impl;

import brew.svc.mem.lgi.service.LoginVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("LoginDAO")
@RequiredArgsConstructor
public class LoginDAO {

    private final SqlSessionTemplate sqlSession;

    int selectSignInYn(LoginVO vo) {
        return sqlSession.selectOne("LoginDAO.selectSignInYn", vo);
    }

    int insertKakaoLogin(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertKakaoLogin", vo);
    }

    int insertKakaoSignIn(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertKakaoSignIn", vo);
    }

    LoginVO selectKakaoLogin(LoginVO vo) {
        return sqlSession.selectOne("LoginDAO.selectKakaoLogin", vo);
    }
}
