package brew.mng.uss.ull.service.impl;

import brew.mng.uss.ull.service.LogInLogVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LoginLogDAO")
@RequiredArgsConstructor
public class LoginLogDAO {

    private final SqlSessionTemplate sqlSession;

    public List<LogInLogVO> selectLoginLogList(LogInLogVO vo) {
        return sqlSession.selectList("LoginLogDAO.selectLoginLogList", vo);
    }

    public List<LogInLogVO> selectLoginLog(LogInLogVO vo) {
        return sqlSession.selectList("LoginLogDAO.selectLoginLog", vo);
    }
}
