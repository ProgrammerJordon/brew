package brew.cmm.service.log.vst.service.impl;

import brew.cmm.service.log.vst.service.BrewVisitorVO;
import brew.cmm.service.ppt.BrewIdGnrProperties;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("BrewVisitorDAO")
@RequiredArgsConstructor
public class BrewVisitorDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    public void insertVisitorLog(BrewVisitorVO vo) {
        vo.setLogId(brewIdGnrProperties.getNextLogId());
        sqlSession.insert("BrewVisitorDAO.insertVisitorLog", vo);
    }

}
