package brew.mng.trd.ord.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOrder {

    private List<StockOrderVO> stockOrderVOList;
    private StockOrderVO stockOrderVO;
}
