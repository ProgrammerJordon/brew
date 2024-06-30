package brew.svc.csc.cst.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConsultVO extends CommonVO implements Serializable {

    private int sn;
    private String title;
    private String contents;
    private String atchfileId;
    private int inqCnt;

    private int rnum;

}
