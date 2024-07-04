package brew.mng.cmm.bbs.web;

import brew.cmm.service.fms.service.FileMngService;
import brew.cmm.service.fms.service.FileVO;
import brew.cmm.service.ppt.BrewProperties;
import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.BoardVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mng/cmm/bbs")
@RequiredArgsConstructor
public class BoardController {

    private final FileMngService fileMngService;
    private final BoardService boardService;


    @RequestMapping("/selectBoardListVw.do")
    public String selectBoardListVw() {
        return "/mng/cmm/bbs/selectBoardListVw";
    }

    @RequestMapping("/selectBoardList.do")
    @ResponseBody
    public Board selectBoardList(@RequestBody BoardVO vo, HttpServletResponse response) {
        return boardService.selectBoardList(vo);
    }

    @RequestMapping("/insertBoardVw.do")
    public String insertBoardVw() {
        return "/mng/cmm/bbs/insertBoardVw";
    }

    @RequestMapping("/insertBoard.do")
    @ResponseBody
    public Board insertBoard(@RequestBody BoardVO vo) {
        return boardService.insertBoard(vo);
    }

    @RequestMapping("/selectBoardDtlsVw.do")
    public String selectBoardDtlsVw(@RequestParam(name = "sn", required = false) String sn,
                                    BoardVO vo,
                                    FileVO fvo,
                                    Model model) throws Exception {

        // atchFileId 에 접근하기 위한 조회
        vo.setSn(Integer.parseInt(sn));
        Board rs = boardService.selectBoardDtls(vo);

        fvo.setAtchFileId(rs.getBoardVO().getAtchFileId());
        List<FileVO> fvoList = fileMngService.selectFileInfs(fvo);

        model.addAttribute("sn", sn);
        model.addAttribute("atchFileId", rs.getBoardVO().getAtchFileId());
        model.addAttribute("fileList", fvoList);

        return "/mng/cmm/bbs/selectBoardDtlsVw";
    }

    @RequestMapping("/selectBoardDtls.do")
    @ResponseBody
    public Board selectBoardDtls(@RequestBody BoardVO vo) {
        return boardService.selectBoardDtls(vo);
    }

    @RequestMapping("/updateBoard.do")
    @ResponseBody
    public Board updateBoard(@RequestBody BoardVO vo) {

        String atchFileID = vo.getAtchFileId();

        if(!vo.getAtchFileId().equals("")) {
            vo.setAtchFileId(atchFileID);
        }else {
            vo.setAtchFileId(null);
        }

        return boardService.updateBoard(vo);
    }

    @RequestMapping("/deleteBoard.do")
    @ResponseBody
    public Board deleteBoard(@RequestBody BoardVO vo) {
        return boardService.deleteBoard(vo);
    }
}
