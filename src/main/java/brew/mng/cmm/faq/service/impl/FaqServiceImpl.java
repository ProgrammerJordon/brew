package brew.mng.cmm.faq.service.impl;

import brew.mng.cmm.faq.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("FaqService")
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqDAO faqDAO;

}
