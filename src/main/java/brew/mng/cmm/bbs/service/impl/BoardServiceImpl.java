package brew.mng.cmm.bbs.service.impl;

import brew.cmm.service.fms.service.FileVO;
import brew.cmm.service.fms.service.impl.FileManageDAO;
import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.BoardVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("BoardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final FileManageDAO fileManageDAO;
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

    @Transactional
    @Override
    public Board deleteBoard(BoardVO vo) throws Exception {

        if(vo.getAtchFileId() != null) {

            FileVO fvo = new FileVO();
            fvo.setAtchFileId(vo.getAtchFileId());

            // atchFileId로 조회한 fileList
            List<FileVO> fvoList = fileManageDAO.selectFileInfs(fvo);

            // 파일상세 제거
            for(int i = 0 ; i < fvoList.size() ; i++) {
                fileManageDAO.deleteFileInf(fvoList.get(i));
            }
            // 파일속성제거
            fileManageDAO.deleteCOMTNFILE(fvo);
        }

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
