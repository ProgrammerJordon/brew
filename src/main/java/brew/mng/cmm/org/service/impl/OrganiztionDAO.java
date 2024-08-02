package brew.mng.cmm.org.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("OrganiztionDAO")
@RequiredArgsConstructor
public class OrganiztionDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

}
