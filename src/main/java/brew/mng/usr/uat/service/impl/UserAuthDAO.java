package brew.mng.usr.uat.service.impl;

import brew.mng.usr.uat.service.UserAuthVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserAuthDAO")
@RequiredArgsConstructor
public class UserAuthDAO {

    private final SqlSessionTemplate sqlSession;

    List<UserAuthVO> selectUserAuthList(UserAuthVO vo) {
        return sqlSession.selectList("UserAuthDAO.selectUserAuthList", vo);
    }

    UserAuthVO selectUserAuthDtls(UserAuthVO vo) {
        return sqlSession.selectOne("UserAuthDAO.selectUserAuthDtls", vo);
    }

    int updateUserAuth(UserAuthVO vo) {
        return sqlSession.update("UserAuthDAO.updateUserAuth", vo);
    }
}
