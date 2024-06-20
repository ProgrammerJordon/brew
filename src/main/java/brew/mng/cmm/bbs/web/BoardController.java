package brew.mng.cmm.bbs.web;

import brew.mng.cmm.bbs.service.Board;
import brew.mng.cmm.bbs.service.BoardService;
import brew.mng.cmm.bbs.service.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/cmm/bbs")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("/selectBoardListVw.do")
    public String selectBoardListVw() {
        return "/mng/cmm/bbs/selectBoardListVw";
    }

    @RequestMapping("/selectBoardList.do")
    @ResponseBody
    public Board selectBoardList(@RequestBody BoardVO vo) {
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


}
