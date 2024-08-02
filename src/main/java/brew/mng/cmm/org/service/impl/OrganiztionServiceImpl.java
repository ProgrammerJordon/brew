package brew.mng.cmm.org.service.impl;

import brew.mng.cmm.org.service.OrganiztionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("OrganiztionService")
@RequiredArgsConstructor
public class OrganiztionServiceImpl implements OrganiztionService {

    private final OrganiztionDAO organiztionDAO;


}
