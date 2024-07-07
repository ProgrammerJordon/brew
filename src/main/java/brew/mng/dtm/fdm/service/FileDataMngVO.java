package brew.mng.dtm.fdm.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class FileDataMngVO extends CommonVO implements Serializable {

    private String atchFileId;
    private int fileSn;
    private String orignlFileNm;
    private String fileExtsn;
    private String fileSize;

    private int rnum;
}
