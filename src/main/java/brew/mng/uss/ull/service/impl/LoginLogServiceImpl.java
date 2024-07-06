package brew.mng.uss.ull.service.impl;

import brew.mng.uss.ull.service.LogInLogVO;
import brew.mng.uss.ull.service.LoginLog;
import brew.mng.uss.ull.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("LoginLogService")
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogDAO loginLogDAO;

    @Override
    public LoginLog selectLoginLogList(LogInLogVO vo) {
        return LoginLog.builder()
                .logInLogVOList(loginLogDAO.selectLoginLogList(vo))
                .build();
    }
}
