package brew.cmm.service.log.vst.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class BrewVisitorVO extends CommonVO implements Serializable {

    private String logId;
    private String sessionId;
    private String clientIp;
}
