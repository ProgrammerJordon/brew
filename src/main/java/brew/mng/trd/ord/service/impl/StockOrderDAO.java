package brew.mng.trd.ord.service.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository("StockOrderDAO")
@RequiredArgsConstructor
public class StockOrderDAO {

    private final SqlSessionTemplate sqlSession;
}
