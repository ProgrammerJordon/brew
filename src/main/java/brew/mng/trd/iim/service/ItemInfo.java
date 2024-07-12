package brew.mng.trd.iim.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfo {

    private List<ItemInfoVO> itemInfoVOList;
    private ItemInfoVO itemInfoVO;
}
