package brew.mng.trd.iim.service.impl;

import brew.mng.trd.iim.service.ItemInfoVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ItemInfoDAO")
@RequiredArgsConstructor
public class ItemInfoDAO {

    private final SqlSessionTemplate sqlSession;

    public List<ItemInfoVO> selectItemInfoList(ItemInfoVO vo) {
        return sqlSession.selectList("ItemInfoDAO.selectItemInfoList", vo);
    }

    public int insertItemInfoBatch(ItemInfoVO vo) {
        return sqlSession.insert("ItemInfoDAO.insertItemInfoBatch", vo);
    }

    public int updateTruncateItemInfo(ItemInfoVO vo) {
        return sqlSession.update("ItemInfoDAO.updateTruncateItemInfo", vo);
    }
}
