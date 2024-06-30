package brew.svc.csc.cst.service.Impl;

import brew.svc.csc.cst.service.ConsultVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ConsultDAO")
@RequiredArgsConstructor
public class ConsultDAO {

    private final SqlSessionTemplate sqlSession;

    int insertConsult(ConsultVO vo) {
        return sqlSession.insert("ConsultDAO.insertConsult", vo);
    }

    List<ConsultVO> selectConsultList(ConsultVO vo) {
        return sqlSession.selectList("ConsultDAO.selectConsultList", vo);
    }
}
