package brew.mng.cmm.faq.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Faq {

    private List<FaqVO> faqVOList;
    private FaqVO faqVO;
}
