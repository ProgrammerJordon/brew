package brew.mng.cmm.bbs.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class BoardVO extends CommonVO implements Serializable {

    private int sn;
    private String title;
    private String contents;
    private int inqCnt;

    private int rnum;
    private String mainYn; // 메인표출여부

}
