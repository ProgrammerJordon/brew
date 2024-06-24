package brew.mng.cmm.faq.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface FaqService {

    public Faq selectFaqList(FaqVO vo);

    public Faq insertFaq(FaqVO vo);

    public Faq selectFaqDtls(FaqVO vo);

    public Faq updateFaq(FaqVO vo);
    public Faq deleteFaq(FaqVO vo);

}
