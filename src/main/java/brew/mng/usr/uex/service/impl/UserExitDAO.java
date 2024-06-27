package brew.mng.usr.uex.service.impl;

import brew.mng.usr.uex.service.UserExitVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserExitDAO")
@RequiredArgsConstructor
public class UserExitDAO {

    private final SqlSessionTemplate sqlSession;

    List<UserExitVO> selectUserExitList(UserExitVO vo) {
        return sqlSession.selectList("UserExitDAO.selectUserExitList", vo);
    }

    int updateUserExit(UserExitVO vo) {
        return sqlSession.update("UserExitDAO.updateUserExit", vo);
    }

    int updateUserLoginExit(UserExitVO vo) {
        return sqlSession.update("UserExitDAO.updateUserLoginExit", vo);
    }
}
