package brew.cmm.service.log.vst.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class BrewVisitorVO extends CommonVO implements Serializable {

    private String logId;
    private String sessionId;
    private String clientIp;
}
