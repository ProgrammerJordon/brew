package brew.mng.cmm.org.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrganizationVO extends CommonVO implements Serializable {

    private String orgId;
    private String orgNm;
    private String telNo;
    private int orgOrdr;
}
