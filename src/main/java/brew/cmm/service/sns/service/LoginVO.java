package brew.cmm.service.sns.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginVO extends CommonVO implements Serializable {

    private String userSn;
    private String userId;
    private String loginSe;
    private String nickNm;
    private String profileImgUrl;
    private String thumbnailImgUrl;

    private String email;
    private String emailVerified;

    private String userNm;
    private String givenNm;
    private String familyNm;

    private String birthYear;
    private String birthDay;
    private String mobile;
    private String mobileE;
    private String age;
    private String gender;


    private String accessToken;
    private String useYn;
    private String authCd;
    private String exitYn;

    private int count;
    private int result;


}
