package brew.mng.min.idx.service;

import brew.mng.cmm.bbs.service.BoardVO;
import brew.mng.cmm.faq.service.FaqVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Main {

    private List<MainVO> mainVOListl;
    private MainVO mainVO;

    private List<BoardVO> boardVOList;
    private BoardVO boardVO;

    private List<FaqVO> faqVOList;
    private FaqVO faqVO;
}
