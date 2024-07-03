package brew.cmm.service.sns.service.impl;

import brew.cmm.service.sns.service.LoginVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("LoginDAO")
@RequiredArgsConstructor
public class LoginDAO {

    private final SqlSessionTemplate sqlSession;

    // Brew 가입여부 확인
    int selectSignInYn(LoginVO vo) {
        return sqlSession.selectOne("LoginDAO.selectSignInYn", vo);
    }

    // 카카오 유저정보 조회
    LoginVO selectUserLoginInfo(LoginVO vo) {
        return sqlSession.selectOne("LoginDAO.selectUserLoginInfo", vo);
    }

    // 카카오 가입
    int insertKakaoLogin(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertKakaoLogin", vo);
    }
    // 카카오 가입내역
    int insertKakaoSignIn(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertKakaoSignIn", vo);
    }

    // 구글 가입
    int insertGoogleLogin(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertGoogleLogin", vo);
    }
    // 구글 가입내역
    int insertGoogleSignIn(LoginVO vo) {
        return sqlSession.insert("LoginDAO.insertGoogleSignIn", vo);
    }
}
