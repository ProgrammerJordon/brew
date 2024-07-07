package brew.mng.cmm.faq.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import brew.mng.cmm.faq.service.FaqVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FaqDAO")
@RequiredArgsConstructor
public class FaqDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    List<FaqVO> selectFaqList(FaqVO vo) {
        return sqlSession.selectList("FaqDAO.selectFaqList", vo);
    }

    int insertFaq(FaqVO vo) {
        vo.setFaqId(brewIdGnrProperties.getNextFaqId());
        return sqlSession.insert("FaqDAO.insertFaq", vo);
    }

    FaqVO selectFaqDtls(FaqVO vo) {
        return sqlSession.selectOne("FaqDAO.selectFaqDtls", vo);
    }

    int updateFaq(FaqVO vo) {
        return sqlSession.update("FaqDAO.updateFaq", vo);
    }

    int deleteFaq(FaqVO vo) {
        return sqlSession.delete("FaqDAO.deleteFaq", vo);
    }

}
