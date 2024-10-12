package brew.svc.csc.rev.service.Impl;

import brew.svc.csc.rev.service.Review;
import brew.svc.csc.rev.service.ReviewService;
import brew.svc.csc.rev.service.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("ReviewService")
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDAO reviewDAO;

    @Override
    public Review selectReviewList(ReviewVO vo) {

        return Review.builder()
                .reviewVOList(reviewDAO.selectReviewList(vo))
                .build();
    }

}
