package brew.mng.cmm.bbs.service;

import lombok.Data;

@Data
public class BoardVO {

    private int sn;
    private String title;
    private String contents;
    private int inqCnt;
    private String atchFileId;
    private String rgtrId;
    private String rgtrDt;
    private String mdfnId;
    private String mdfnDt;
}
