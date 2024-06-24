package brew.mng.cmm.faq.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class FaqVO extends CommonVO implements Serializable {

    private int sn;
    private String title;
    private String contents;
    private int inqCnt;

    private int rnum;
}
