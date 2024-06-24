package brew.mng.cmm.ccd.service.impl;

import brew.mng.cmm.ccd.service.Code;
import brew.mng.cmm.ccd.service.CodeVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CodeDAO")
@RequiredArgsConstructor
public class CodeDAO {

    private final SqlSessionTemplate sqlSession;

    List<CodeVO> selectCodeList(CodeVO vo) {
        return sqlSession.selectList("CodeDAO.selectCodeList", vo);
    }
    int insertCode(CodeVO vo) {
        return sqlSession.insert("CodeDAO.insertCode", vo);
    }

    CodeVO selectCode(CodeVO vo) {
        return sqlSession.selectOne("CodeDAO.selectCode", vo);
    }
    int updateCode(CodeVO vo) {
        return sqlSession.update("CodeDAO.updateCode", vo);
    }
}
