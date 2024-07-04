package brew.mng.cmm.bbs.service;

import org.springframework.web.bind.annotation.RequestBody;

public interface BoardService {

    public Board selectBoardList(BoardVO vo);
    public Board insertBoard(BoardVO vo);
    public Board selectBoardDtls(BoardVO vo);
    public Board updateBoard(BoardVO vo);
    public Board deleteBoard(BoardVO vo) throws Exception;
}
