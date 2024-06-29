package brew.mng.cmm.faq.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class FaqVO extends CommonVO implements Serializable {

    private int sn;
    private String title;
    private String contents;
    private int inqCnt;

    private int rnum;
    private String mainYn; // 메인 표출여부
}
