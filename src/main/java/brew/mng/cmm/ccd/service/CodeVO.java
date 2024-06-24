package brew.mng.cmm.ccd.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class CodeVO extends CommonVO implements Serializable {

    private String codeId;
    private String code;
    private String codeNm;
    private String codeDc;
    private String useYn;

    private int rnum;

}
