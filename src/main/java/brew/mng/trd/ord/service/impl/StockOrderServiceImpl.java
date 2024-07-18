package brew.mng.trd.ord.service.impl;

import brew.mng.trd.ord.service.StockOrder;
import brew.mng.trd.ord.service.StockOrderService;
import brew.mng.trd.ord.service.StockOrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("StockOrderService")
@RequiredArgsConstructor
public class StockOrderServiceImpl implements StockOrderService {

    private final StockOrderDAO stockOrderDAO;


    @Override
    public StockOrder searchConditionItem(StockOrderVO vo) {
        return StockOrder.builder()
                .stockOrderVOList(stockOrderDAO.searchConditionItem(vo))
                .build();
    }
}
