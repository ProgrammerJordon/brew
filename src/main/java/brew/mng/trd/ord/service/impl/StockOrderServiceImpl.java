package brew.mng.trd.ord.service.impl;

import brew.mng.trd.ord.service.StockOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("StockOrderService")
@RequiredArgsConstructor
public class StockOrderServiceImpl implements StockOrderService {

    private final StockOrderDAO stockOrderDAO;


}
