package brew.mng.trd.ord.service.impl;

import brew.mng.trd.ord.service.StockOrderVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StockOrderDAO")
@RequiredArgsConstructor
public class StockOrderDAO {

    private final SqlSessionTemplate sqlSession;

    List<StockOrderVO> searchConditionItem(StockOrderVO vo) {
        return sqlSession.selectList("StockOrderDAO.searchConditionItem", vo);
    }
}
