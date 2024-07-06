package brew.mng.uss.uvc.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class VisitorCntVO extends CommonVO implements Serializable {

    private String logId;
    private String clientIp;

    private int rnum;
    private int count;
    private String se;
}
