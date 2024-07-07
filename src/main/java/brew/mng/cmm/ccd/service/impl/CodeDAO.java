package brew.mng.cmm.ccd.service.impl;

import brew.cmm.service.ppt.BrewIdGnrProperties;
import brew.mng.cmm.ccd.service.Code;
import brew.mng.cmm.ccd.service.CodeVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CodeDAO")
@RequiredArgsConstructor
public class CodeDAO {

    private final BrewIdGnrProperties brewIdGnrProperties;
    private final SqlSessionTemplate sqlSession;

    List<CodeVO> selectCodeList(CodeVO vo) {
        return sqlSession.selectList("CodeDAO.selectCodeList", vo);
    }
    int insertCode(CodeVO vo) {
        vo.setCodeId(brewIdGnrProperties.getNextCodeId());
        return sqlSession.insert("CodeDAO.insertCode", vo);
    }

    CodeVO selectCode(CodeVO vo) {
        return sqlSession.selectOne("CodeDAO.selectCode", vo);
    }
    int updateCode(CodeVO vo) {
        return sqlSession.update("CodeDAO.updateCode", vo);
    }
    int selectCodedtlsCnt(CodeVO vo) {
        return sqlSession.selectOne("CodeDAO.selectCodedtlsCnt", vo);
    }
    int deleteCode(CodeVO vo) {
        return sqlSession.delete("CodeDAO.deleteCode", vo);
    }
    int insertCodeDtls(CodeVO vo) {
        return sqlSession.insert("CodeDAO.insertCodeDtls",vo);
    }
    List<CodeVO> selectCodeDtlsList(CodeVO vo) {
        return sqlSession.selectList("CodeDAO.selectCodeDtlsList", vo);
    }
    CodeVO selectCodedtls(CodeVO vo) {
        return sqlSession.selectOne("CodeDAO.selectCodedtls", vo);
    }
    int updateCodedtls(CodeVO vo) {
        return sqlSession.update("CodeDAO.updateCodedtls", vo);
    }
    int deleteCodedtls(CodeVO vo) {
        return sqlSession.delete("CodeDAO.deleteCodedtls", vo);
    }
}
