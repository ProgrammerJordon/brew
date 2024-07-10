package brew.mng.uss.ull.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class LogInLogVO extends CommonVO implements Serializable {

    private String logId;
    private String loginSe;
    private String userSn;
    private String nickNm;

    private int rnum;
    private int kakaoCount;
    private int naverCount;
    private int googleCount;

}
