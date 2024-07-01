package brew.mng.cmm.bbs.service.impl;

import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("BoardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardDAO boardDAO;

    @Override
    public Board selectBoardList(BoardVO vo) {
        return Board.builder()
                .boardVOList(boardDAO.selectBoardList(vo))
                .boardVO(vo)
                .build();
    }

    @Override
    public Board insertBoard(BoardVO vo) {
        int result = boardDAO.insertBoard(vo);
        if(result == 1) {
            vo.setResultMessage("정상적으로 공지사항을 등록하였습니다.");
        }else {
            vo.setResultMessage("공지사항 등록을 실패하였습니다.");
        }
        return Board.builder()
                .boardVO(vo)
                .build();
    }

    @Override
    public Board selectBoardDtls(BoardVO vo) {
        return Board.builder()
                .boardVO(boardDAO.selectBoardDtls(vo))
                .build();
    }

    @Override
    public Board updateBoard(BoardVO vo) {
        int result = boardDAO.updateBoard(vo);
        if(result == 1) {
            vo.setResultMessage("공지사항이 정상적으로 수정되었습니다.");
        }else {
            vo.setResultMessage("공지사항 수정을 실패하였습니다.");
        }
        return Board.builder()
                .boardVO(vo)
                .build();
    }

    @Override
    public Board deleteBoard(BoardVO vo) {
        int result = boardDAO.deleteBoard(vo);
        if(result == 1) {
            vo.setResultMessage("공지사항이 정상적으로 삭제되어습니다.");
        }else {
            vo.setResultMessage("공지사항 삭제를 실패하였습니다.");
        }
        return Board.builder()
                .boardVO(vo)
                .build();
    }
}
