package brew.mng.trd.ord.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class StockOrderVO extends CommonVO implements Serializable {

    private String account;
    private Object res;
}
