package brew.mng.usr.usm.service.impl;

import brew.mng.usr.usm.service.UserVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDAO")
@RequiredArgsConstructor
public class UserDAO {

    private final SqlSessionTemplate sqlSession;

    List<UserVO> selectUserMngList(UserVO vo) {
        return sqlSession.selectList("UserDAO.selectUserMngList", vo);
    }
}
