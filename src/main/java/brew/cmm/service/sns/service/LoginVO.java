package brew.cmm.service.sns.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginVO extends CommonVO implements Serializable {

    // 구글, 네이버, 카카오 사용자정보 파라미터
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

    // 권한
    private String accessToken;
    private String useYn;
    private String authCd;
    private String exitYn;

    // 로그인결과값
    private int count;
    private int result;

    // 로그
    private String logId;


}
