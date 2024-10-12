package brew.svc.csc.rev.service;

import brew.svc.csc.not.service.NoticeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private List<ReviewVO> reviewVOList;
    private ReviewVO reviewVO;
}
