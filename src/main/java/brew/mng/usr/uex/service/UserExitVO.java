package brew.mng.usr.uex.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserExitVO extends CommonVO implements Serializable {

    private String exitSn;
    private String userSn;
    private String useYn;
    private String loginSe;
    private String authCd;
    private String exitYn;

    private String userId;
    private String userNm;
    private String nickNm;
    private String profileImgUrl;
    private String thumbnailImgUrl;
    private String revivalYn;

    private int rnum;
    private Object chk;
}
