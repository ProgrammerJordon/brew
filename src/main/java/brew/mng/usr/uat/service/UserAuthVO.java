package brew.mng.usr.uat.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserAuthVO extends CommonVO implements Serializable {

    private String userSn;
}
