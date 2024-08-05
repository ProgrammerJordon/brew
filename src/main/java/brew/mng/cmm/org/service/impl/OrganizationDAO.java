package brew.mng.cmm.org.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import brew.mng.cmm.org.service.OrganizationVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("OrganizationDAO")
@RequiredArgsConstructor
public class OrganizationDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    int insertOranization(OrganizationVO vo) {
        vo.setOrgId(brewIdGnrProperties.getNextOrgId());
        return sqlSession.insert("OrganizationDAO.insertOranization", vo);
    }

}
