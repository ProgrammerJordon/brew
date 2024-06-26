package brew.mng.usr.usm.service.impl;

import brew.mng.usr.usm.service.User;
import brew.mng.usr.usm.service.UserService;
import brew.mng.usr.usm.service.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User selectUserMngList(UserVO vo) {
        return User.builder()
                .userVOList(userDAO.selectUserMngList(vo))
                .build();
    }
}
