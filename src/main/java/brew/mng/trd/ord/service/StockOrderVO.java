package brew.mng.trd.ord.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class StockOrderVO extends CommonVO implements Serializable {

    private String account; // 계좌
    private String pdno; // 종목번호
    private String ordDvsn; // 주문구분
    private String ordQty; // 주문주식수
    private String ordUnpr; // 주문단가
    private Object res; // 응답 (JSON TYPE)

    private String ordGnoBrno; // 한국투자증권 채번번호
    private String odno; // 원주문번호
    private String ordDvsnCd; // 주문구분 // 시장가 / 지정가
    private String dwmy; // 일주월년


    private String mrktCtg;
    private String srtnCd;
    private String itmsNm;

    private int highPrice;
    private int nowPrice;

}
