package brew.mng.dtm.mdm.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class MetadataVO extends CommonVO implements Serializable {

    private String mdmId; // 메타데이터ID
    private String datasetNm; // 데이터셋명
    private String datasetEngNm; // 데이터셋영문명
    private String datasetClsfCd; // 데이터셋분류
    private String authCd; // 권한코드
    private String sceLevel; // 보안레벨
    private String deptNm; // 부서명
    private String explain; // 설명
    private String atchFileId; // 첨부파일ID

    private int inqCnt; // 조회수
    private int rnum;
}
