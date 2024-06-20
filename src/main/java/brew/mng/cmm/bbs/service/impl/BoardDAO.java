package brew.mng.cmm.bbs.service.impl;

import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BoardDAO")
@RequiredArgsConstructor
public class BoardDAO {

    private final SqlSessionTemplate sqlSession;

    List<BoardVO> selectBoardList(BoardVO vo) {
        return sqlSession.selectList("BoardDAO.selectBoardList", vo);
    }
    int insertBoard(BoardVO vo) {
        return sqlSession.insert("BoardDAO.insertBoard", vo);
    }
}
