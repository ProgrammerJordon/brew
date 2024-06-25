package brew.mng.usr.uat.service.impl;

import brew.mng.usr.uat.service.UserAuth;
import brew.mng.usr.uat.service.UserAuthService;
import brew.mng.usr.uat.service.UserAuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserAuthService")
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthDAO userAuthDAO;

    @Override
    public UserAuth selectUserAuthList(UserAuthVO vo) {
        return UserAuth.builder()
                .userAuthVOList(userAuthDAO.selectUserAuthList(vo))
                .build();
    }
}
