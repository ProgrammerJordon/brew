package brew.mng.dtm.fdm.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class FileDataMngVO extends CommonVO implements Serializable {

    private String atchFileId;
    private int fileSn;
    private String streFileNm;
    private String orignlFileNm;
    private String fileExtsn;
    private String fileSize;

    private int rnum;
}
