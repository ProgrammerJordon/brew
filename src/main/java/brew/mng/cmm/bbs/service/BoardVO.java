package brew.mng.cmm.bbs.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class BoardVO extends CommonVO implements Serializable {

    private int sn;
    private String title;
    private String contents;
    private int inqCnt;

    private int rnum;

}
