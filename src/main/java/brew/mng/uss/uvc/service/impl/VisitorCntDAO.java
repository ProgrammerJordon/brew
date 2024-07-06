package brew.mng.uss.uvc.service.impl;

import brew.mng.uss.uvc.service.VisitorCntVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("VisitorCntDAO")
@RequiredArgsConstructor
public class VisitorCntDAO {

    private final SqlSessionTemplate sqlSession;

    List<VisitorCntVO> selectVisitorCntList(VisitorCntVO vo) {
        return sqlSession.selectList("VisitorCntDAO.selectVisitorCntList", vo);
    }

    List<VisitorCntVO> selectVisitorCnt(VisitorCntVO vo) {
        return sqlSession.selectList("VisitorCntDAO.selectVisitorCnt", vo);
    }
}
