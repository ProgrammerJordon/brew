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

        }
        return Board.builder()
                .boardVO(vo)
                .build();
    }
}
