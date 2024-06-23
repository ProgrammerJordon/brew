package brew.mng.cmm.faq.service.impl;

import brew.mng.cmm.bbs.service.impl.BoardDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("FaqService")
@RequiredArgsConstructor
public class FaqServiceImpl {

    private final FaqDAO faqDAO;

}
