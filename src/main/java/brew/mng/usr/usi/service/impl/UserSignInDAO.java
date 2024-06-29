package brew.mng.usr.usi.service.impl;

import brew.mng.usr.usi.service.UserSignInVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserSignInDAO")
@RequiredArgsConstructor
public class UserSignInDAO {

    private final SqlSessionTemplate sqlSession;

    public List<UserSignInVO> selectUserSignInList(UserSignInVO vo) {
        return sqlSession.selectList("UserSignInDAO.selectUserSignInList", vo);
    }
}
