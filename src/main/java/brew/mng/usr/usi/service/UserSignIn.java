package brew.mng.usr.usi.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignIn {

    private List<UserSignInVO> userSignInVOList;
    private UserSignInVO userSignInVO;

}
