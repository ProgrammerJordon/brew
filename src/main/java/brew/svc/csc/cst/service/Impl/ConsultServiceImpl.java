package brew.svc.csc.cst.service.Impl;

import brew.svc.csc.cst.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("ConsultService")
@RequiredArgsConstructor
public class ConsultServiceImpl implements ConsultService {

    private final ConsultDAO consultDAO;

}
