package brew.mng.dtm.fdm.web;

import brew.cmm.util.BrewCommonUtil;
import brew.mng.dtm.fdm.service.FileDataMng;
import brew.mng.dtm.fdm.service.FileDataMngService;
import brew.mng.dtm.fdm.service.FileDataMngVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/dtm/fdm")
@RequiredArgsConstructor
public class FileDataMngController {

    private final BrewCommonUtil brewCommonUtil;
    private final FileDataMngService fileDataMngService;

    @RequestMapping("/selectFileDataMngListVw.do")
    public String selectFileDataMngListVw() {
        return "/mng/dtm/fdm/selectFileDataMngListVw";
    }

    @RequestMapping("/selectFileDataMngList.do")
    @ResponseBody
    public FileDataMng selectFileDataMngList(@RequestBody FileDataMngVO vo) {
        return fileDataMngService.selectFileDataMngList(vo);
    }

    @RequestMapping("/downloadAtchFile.do")
    public void downloadAtchFile(@ModelAttribute FileDataMngVO vo,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        brewCommonUtil.downloadAtchFile(vo.getAtchFileId(), String.valueOf(vo.getFileSn()), request, response);
    }
}
