package brew.mng.uss.uvc.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class VisitorCntVO extends CommonVO implements Serializable {

    private String logId;
    private String clientIp;

    private int rnum;
    private int count;
    private String se;
}
