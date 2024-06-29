package brew.mng.usr.usi.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSignInVO extends CommonVO implements Serializable {

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

    private int rnum;
}
