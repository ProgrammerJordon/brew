package brew.mng.cmm.faq.service.impl;

import brew.mng.cmm.faq.service.Faq;
import brew.mng.cmm.faq.service.FaqService;
import brew.mng.cmm.faq.service.FaqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("FaqService")
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqDAO faqDAO;

    @Override
    public Faq selectFaqList(FaqVO vo) {
        return Faq.builder()
                .faqVOList(faqDAO.selectFaqList(vo))
                .build();
    }

    @Override
    public Faq insertFaq(FaqVO vo) {

        int result = faqDAO.insertFaq(vo);
        if(result == 1) {
            vo.setResultMessage("FAQ가 정상적으로 등록되었습니다.");
        }else {
            vo.setResultMessage("FAQ 등록을 실패하였습니다.");
        }

        return Faq.builder()
                .faqVO(vo)
                .build();
    }

    @Override
    public Faq selectFaqDtls(FaqVO vo) {
        return Faq.builder()
                .faqVO(faqDAO.selectFaqDtls(vo))
                .build();
    }

    @Override
    public Faq updateFaq(FaqVO vo) {
        int result = faqDAO.updateFaq(vo);
        if(result == 1) {
            vo.setResultMessage("FAQ가 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("FAQ 수정에 실패하였습니다.");
        }
        return Faq.builder()
                .faqVO(vo)
                .build();
    }

    @Override
    public Faq deleteFaq(FaqVO vo) {
        int result = faqDAO.deleteFaq(vo);
        if(result == 1) {
            vo.setResultMessage("FAQ가 정상적으로 삭제되었습니다.");
        }else {
            vo.setResultMessage("FAQ 삭제를 실패하였습니다.");
        }
        return Faq.builder()
                .faqVO(vo)
                .build();
    }
}
