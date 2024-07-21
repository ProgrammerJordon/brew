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
                .noticeVOList(noticeDAO.selectNoticeList(vo))
                .build();
    }

    @Override
    public Notice insertNotice(NoticeVO vo) {
        int insert_result = noticeDAO.insertNotice(vo);

        if (insert_result == 1) {
            vo.setResultMessage("공지사항 등록이 완료 되었습니다.");
        } else{
            vo.setResultMessage("공지사항 등록이 실패 하였습니다.\n지속적으로 등록되지 않을 경우 관리자에게 문의 해주세요.");
        }

        return Notice.builder()
                .noticeVO(vo)
                .build();
    }

    @Override
    public Notice selectNoticeDtVw(NoticeVO vo) {
        return Notice.builder()
                .noticeVO(noticeDAO.selectNoticeDtVw(vo))
                .build();
    }

    @Override
    public Notice deleteNotice(NoticeVO vo) {
        int delete_result = noticeDAO.deleteNotice(vo);

        if (delete_result == 1) {
            vo.setResultMessage("공지사항 삭제가 완료 되었습니다.");
        } else {
            vo.setResultMessage("공지사항을 삭제하는데 실패 하였습니다.\n지속적으로 실패시 관리자에게 문의 바랍니다.");
        }

        return Notice.builder()
                .noticeVO(vo)
                .build();
    }

    @Override
    public Notice updateNotice(NoticeVO vo) {
        int update_result = noticeDAO.updateNotice(vo);

        if (update_result == 1) {
            vo.setResultMessage("공지사항 수정이 완료 되었습니다.");
        } else {
            vo.setResultMessage("공지사항 수정이 실패 하였습니다.\n지속적으로 실패시 관리자에게 문의 바랍니다.");
        }

        return Notice.builder()
                .noticeVO(vo)
                .build();
    }
}
