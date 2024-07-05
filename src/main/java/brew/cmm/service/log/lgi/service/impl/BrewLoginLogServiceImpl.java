package brew.cmm.service.log.lgi.service.impl;

import brew.cmm.service.log.lgi.service.BrewLoginLogService;
import brew.cmm.service.log.lgi.service.impl.BrewLoginLogDAO;
import brew.cmm.service.sns.service.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BrewLoginLogService")
@RequiredArgsConstructor
public class BrewLoginLogServiceImpl implements BrewLoginLogService {

    private final BrewLoginLogDAO brewLoginLogDAO;

    @Transactional
    @Override
    public void insertLoginLog(LoginVO vo) {
        brewLoginLogDAO.insertLoginLog(vo);
    }
}
