package brew.mng.cmm.faq.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("FaqDAO")
@RequiredArgsConstructor
public class FaqDAO {

    private final SqlSessionTemplate sqlSession;

}
