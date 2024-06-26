package brew.mng.usr.uat.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAuthVO extends CommonVO implements Serializable {

    private String userSn;
    private String userId;
    private String userNm;
    private String nickNm;
    private String profileImgUrl;
    private String thumbnailImgUrl;
    private String loginSe;
    private String authCd;
    private String useYn;
    private String exitYn;

    private int rnum;
}
