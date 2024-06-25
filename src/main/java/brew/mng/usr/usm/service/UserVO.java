package brew.mng.usr.usm.service;

import brew.cmm.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends CommonVO implements Serializable {

    private String userSn;
}
