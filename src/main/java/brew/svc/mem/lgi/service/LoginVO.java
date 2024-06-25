package brew.svc.mem.lgi.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVO extends CommonVO implements Serializable {

    private String userSn;
    private String useYn;
    private String loginSe;
    private String authCd;
    private int count;
    private int result;

    private String userId;
    private String userNm;
    private String nickNm;
    private String profileImgUrl;
    private String thumbnailImgUrl;


}
