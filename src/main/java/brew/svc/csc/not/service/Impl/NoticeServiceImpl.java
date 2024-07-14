package brew.svc.csc.not.service.Impl;

import brew.svc.csc.not.service.Notice;
import brew.svc.csc.not.service.NoticeService;
import brew.svc.csc.not.service.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("NoticeService")
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Override
    public Notice selectNoticeList(NoticeVO vo) {
        return Notice.builder()
                .NoticeVOList(noticeDAO.selectNoticeList(vo))
                .build();
    }
}
