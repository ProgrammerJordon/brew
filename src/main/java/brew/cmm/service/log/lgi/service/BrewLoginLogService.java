package brew.cmm.service.log.lgi.service;

import brew.cmm.service.sns.service.LoginVO;

public interface BrewLoginLogService {

    void insertLoginLog(LoginVO vo);
}
