package brew.mng.trd.iim.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemInfoVO extends CommonVO implements Serializable {

    private String basDt; // 기준일자
    private String srtnCd; // 기준코드
    private String isinCd; // 국제인증고유번호
    private String mrktCtg; // 시장구분
    private String itmsNm; // 종목명
    private String crno; // 법인등록번호
    private String corpNm; // 법인명

    private int rnum;
    private Object res;

}

