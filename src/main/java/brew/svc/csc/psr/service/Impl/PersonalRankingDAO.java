package brew.svc.csc.psr.service.Impl;

import brew.svc.csc.psr.service.PersonalRankingVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PersonalRankingDAO")
@RequiredArgsConstructor
public class PersonalRankingDAO {

    private SqlSession sqlSession;

    public List<PersonalRankingVO> selectPersonalRankingList(PersonalRankingVO vo) {
        return sqlSession.selectList("selectPersonalRankingList", vo);
    }
}
