package brew.mng.usr.uat.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {

    private List<UserAuthVO> userAuthVOList;
    private UserAuthVO userAuthVO;

}
