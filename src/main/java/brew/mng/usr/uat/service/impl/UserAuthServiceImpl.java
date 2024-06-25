package brew.mng.usr.uat.service.impl;

import brew.mng.usr.uat.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("UserAuthService")
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAuthDAO userAuthDAO;
}
