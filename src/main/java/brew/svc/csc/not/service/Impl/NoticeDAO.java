package brew.svc.csc.not.service.Impl;

import brew.svc.csc.not.service.NoticeVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("NoticeDAO")
@RequiredArgsConstructor
public class NoticeDAO {

    private final SqlSession sqlSession;

    public List<NoticeVO> selectNoticeList(NoticeVO vo) {
        return sqlSession.selectList("NoticeDAO.selectNoticeList", vo);
    }
}
