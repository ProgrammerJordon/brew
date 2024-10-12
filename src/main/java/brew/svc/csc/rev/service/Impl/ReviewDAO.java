package brew.svc.csc.rev.service.Impl;

import brew.svc.csc.rev.service.Review;
import brew.svc.csc.rev.service.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ReviewDAO")
@RequiredArgsConstructor
public class ReviewDAO{

    private final SqlSession sqlSession;

    public List<ReviewVO> selectReviewList(ReviewVO vo) {
        return sqlSession.selectList("ReviewDAO.selectReviewList", vo);
    }
}
