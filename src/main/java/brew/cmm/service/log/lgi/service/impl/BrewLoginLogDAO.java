package brew.cmm.service.log.lgi.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import brew.cmm.service.sns.service.LoginVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("BrewLoginLogDAO")
@RequiredArgsConstructor
public class BrewLoginLogDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    public void insertLoginLog(LoginVO vo) {
        vo.setLogId(brewIdGnrProperties.getNextLoginLogId());
        sqlSession.insert("BrewLoginLogDAO.insertLoginLog", vo);
    }
}
