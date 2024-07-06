package brew.mng.uss.ull.service.impl;

import brew.mng.uss.ull.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("LoginLogService")
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogDAO loginLogDAO;
}
