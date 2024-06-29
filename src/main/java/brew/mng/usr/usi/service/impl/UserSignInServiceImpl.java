package brew.mng.usr.usi.service.impl;

import brew.mng.usr.usi.service.UserSignIn;
import brew.mng.usr.usi.service.UserSignInService;
import brew.mng.usr.usi.service.UserSignInVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserSignInService")
@RequiredArgsConstructor
public class UserSignInServiceImpl implements UserSignInService {

    private final UserSignInDAO userSignInDAO;

    @Override
    public UserSignIn selectUserSignInList(UserSignInVO vo) {
        return UserSignIn.builder()
                .userSignInVOList(userSignInDAO.selectUserSignInList(vo))
                .build();
    }
}
