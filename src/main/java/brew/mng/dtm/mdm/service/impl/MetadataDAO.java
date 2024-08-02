package brew.mng.dtm.mdm.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import brew.mng.dtm.mdm.service.MetadataVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MetadataDAO")
@RequiredArgsConstructor
public class MetadataDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    int insertMetadata(MetadataVO vo) {
        vo.setMdmId(brewIdGnrProperties.getNextMdmId());
        return sqlSession.insert("MetadataDAO.insertMetadata", vo);
    }

    List<MetadataVO> selectMetadataList(MetadataVO vo) {
        return sqlSession.selectList("MetadataDAO.selectMetadataList", vo);
    }

    MetadataVO selectMetadataDtls(MetadataVO vo) {
        return sqlSession.selectOne("MetadataDAO.selectMetadataDtls", vo);
    }

    int updateMetadata(MetadataVO vo) {
        return sqlSession.update("MetadataDAO.updateMetadata", vo);
    }
}
