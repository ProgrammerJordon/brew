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

    public List<BoardVO> selectBoardList(BoardVO vo) {
        return sqlSession.selectList("BoardDAO.selectBoardList", vo);
    }
    public int insertBoard(BoardVO vo) {
        return sqlSession.insert("BoardDAO.insertBoard", vo);
    }
    public BoardVO selectBoardDtls(BoardVO vo) {
        return sqlSession.selectOne("BoardDAO.selectBoardDtls", vo);
    }
    public int updateBoard(BoardVO vo) {
        return  sqlSession.update("BoardDAO.updateBoard", vo);
    }
    public int deleteBoard(BoardVO vo) {
        return sqlSession.delete("BoardDAO.deleteBoard", vo);
    }

}

