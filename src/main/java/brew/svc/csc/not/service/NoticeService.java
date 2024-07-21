package brew.svc.csc.not.service;

public interface NoticeService {
    public Notice selectNoticeList(NoticeVO vo);

    public Notice insertNotice(NoticeVO vo);

    public Notice selectNoticeDtVw(NoticeVO vo);

    public Notice deleteNotice(NoticeVO vo);
}
