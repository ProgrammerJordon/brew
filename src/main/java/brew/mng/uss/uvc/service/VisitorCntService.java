package brew.mng.uss.uvc.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface VisitorCntService {

    public VisitorCnt selectVisitorCntList(VisitorCntVO vo);

    public VisitorCnt selectVisitorCnt(VisitorCntVO vo);
}
