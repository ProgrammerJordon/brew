package brew.mng.uss.uvc.service.impl;

import brew.mng.uss.uvc.service.VisitorCnt;
import brew.mng.uss.uvc.service.VisitorCntService;
import brew.mng.uss.uvc.service.VisitorCntVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("VisitorCntService")
@RequiredArgsConstructor
public class VisitorCntServiceImpl implements VisitorCntService {

    private final VisitorCntDAO visitorCntDAO;


    @Override
    public VisitorCnt selectVisitorCntList(VisitorCntVO vo) {
        return VisitorCnt.builder()
                .visitorCntVOList(visitorCntDAO.selectVisitorCntList(vo))
                .build();
    }

    @Override
    public VisitorCnt selectVisitorCnt(VisitorCntVO vo) {
        return VisitorCnt.builder()
                .visitorCntVOList(visitorCntDAO.selectVisitorCnt(vo))
                .build();
    }
}
