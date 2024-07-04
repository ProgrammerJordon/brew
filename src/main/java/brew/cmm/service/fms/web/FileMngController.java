package brew.cmm.service.fms.web;

import brew.cmm.service.fms.service.FileMngService;
import brew.cmm.service.fms.service.FileVO;
import brew.cmm.service.log.BrewBasicLogger;
import brew.cmm.service.rch.BrewResourceCloseHelper;
import brew.cmm.util.BrewBrowerUtil;
import brew.cmm.util.FileMngUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FileMngController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileMngController.class);

    private final FileMngService fileService;

    private final FileMngUtil fileUtil;



    @RequestMapping("/cmm/fms/selectFileInfs.do")
    public String selectFileInfs(@ModelAttribute("searchVO") FileVO fileVO,
                                 @RequestParam Map<String, Object> commandMap,
                                 ModelMap model) throws Exception {

        String atchFileId = (String) commandMap.get("atchFileId");

        ObjectMapper objectMapper = new ObjectMapper();
        String fileListJson = "[]"; // 기본값은 빈 배열

        fileVO.setAtchFileId(atchFileId);

        List<FileVO> result = fileService.selectFileInfs(fileVO);

        for (FileVO file : result) {
            file.setAtchFileId(atchFileId);
        }

        try {
            fileListJson = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("fileList", result);
        model.addAttribute("fileListJson", fileListJson);
        model.addAttribute("updateFlag", "N");
        model.addAttribute("fileListCnt", result.size());
        model.addAttribute("atchFileId", atchFileId);

        return "/mng/cmm/fms/FileListView";
    }

    @RequestMapping("/cmm/fms/selectFileInfsForUpdate.do")
    public String selectFileInfsForUpdate(@ModelAttribute("searchVO") FileVO fileVO,
                                          @RequestParam Map<String, Object> commandMap,
                                          ModelMap model) throws Exception {

        String atchFileId = (String) commandMap.get("atchFileId");

        ObjectMapper objectMapper = new ObjectMapper();
        String fileListJson = "[]"; // 기본값은 빈 배열

        fileVO.setAtchFileId(atchFileId);

        List<FileVO> result = fileService.selectFileInfs(fileVO);

        for (FileVO file : result) {
            file.setAtchFileId(atchFileId);
        }

        try {
            fileListJson = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("fileList", result);
        model.addAttribute("fileListJson", fileListJson);
        model.addAttribute("updateFlag", "Y");
        model.addAttribute("fileListCnt", result.size());
        model.addAttribute("atchFileId", atchFileId);
        model.addAttribute("ext", commandMap.get("ext"));
        model.addAttribute("fileCnt", commandMap.get("fileCount"));
        model.addAttribute("fileSize", commandMap.get("fileSize"));
        model.addAttribute("filePath", commandMap.get("filePath"));
        model.addAttribute("fileKey", commandMap.get("fileKey"));

        return "/mng/cmm/fms/FileListView";
    }

    @RequestMapping("/cmm/fms/deleteFileInfs.do")
    public String deleteFileInf(@ModelAttribute("searchVO") FileVO fileVO) throws Exception {

        fileService.deleteFileInf(fileVO);

        return "blank";

    }

    @RequestMapping("/cmm/fms/insertFileInfs.do")
    public @ResponseBody Map<String, Object> insertFileInf(
            @RequestParam(value = "file", required=false) List<MultipartFile> files,
            @RequestParam("atchFileId") String atchFileId,
            @RequestParam("filePath") String filePath,
            @RequestParam("fileKey") String fileKey
    ) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<FileVO> fileList = null;
        FileVO file =  new FileVO();
        int fileSn = 0;

        if (files != null && !files.isEmpty()) {
            if(!atchFileId.isEmpty()) {
                file.setAtchFileId(atchFileId);
                fileSn = fileService.getMaxFileSN(file);
                fileList = fileUtil.parseFileInf(files, fileKey, fileSn, atchFileId, filePath);

                fileService.updateFileInfs(fileList);
            }else {
                fileList = fileUtil.parseFileInf(files, fileKey, 0, "", filePath);
                atchFileId = fileService.insertFileInfs(fileList);
                result.put("atchFileId", atchFileId);
            }
            result.put("fileInfo", fileList.get(0));
        }
        result.put("status", "success");
        return result;
    }

    @RequestMapping("/cmm/fms/deleteFileInfModule.do")
    public @ResponseBody Map<String, Object> deleteFileInfModule(@RequestBody Map<String, String> param) throws Exception {

        FileVO fvo = new FileVO();
        fvo.setAtchFileId(param.get("atchFileId"));

        List<FileVO> fvoList = fileService.selectFileInfs(fvo);

        for(int i = 0 ; i < fvoList.size() ; i++) {
            fileService.deleteFileInf(fvoList.get(i));
        }

        fileService.deleteFileInf(fvo);

        Map<String, Object> result = new HashMap<>();

        result.put("status", "success");

        return result;
    }

    @RequestMapping(value = "/cmm/fms/FileDown.do")
    public void BrewFileDownload(@RequestParam Map<String, Object> commandMap, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String atchFileId = (String) commandMap.get("atchFileId");

        String fileSn = (String) commandMap.get("fileSn");

        FileVO fileVO = new FileVO();
        fileVO.setAtchFileId(atchFileId);
        fileVO.setFileSn(fileSn);
        FileVO fvo = fileService.selectFileInf(fileVO);

        File uFile = new File(fvo.getFileStrePath(), fvo.getStreFileNm());
        long fSize = uFile.length();

        if (fSize > 0) {
            String mimetype = "application/x-msdownload";

            String userAgent = request.getHeader("User-Agent");
            HashMap<String, String> result = BrewBrowerUtil.getBrowser(userAgent);
            if (!BrewBrowerUtil.MSIE.equals(result.get(BrewBrowerUtil.TYPEKEY))) {
                mimetype = "application/x-stuff";
            }

            String contentDisposition = BrewBrowerUtil.getDisposition(fvo.getOrignlFileNm(), userAgent, "UTF-8");
            response.setContentType(mimetype);
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentLengthLong(fSize);

            BufferedInputStream in = null;
            BufferedOutputStream out = null;

            try {
                in = new BufferedInputStream(new FileInputStream(uFile));
                out = new BufferedOutputStream(response.getOutputStream());

                FileCopyUtils.copy(in, out);
                out.flush();
            } catch (IOException ex) {
                BrewBasicLogger.ignore("IO Exception", ex);
            } finally {
                BrewResourceCloseHelper.close(in, out);
            }

        }else {
            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<script type='text/javascript'>");
            out.println("alert('파일이 존재하지 않습니다.');");
            out.println("window.close();"); // 현재 창 닫기
            out.println("</script>");

            out.flush();
            out.close();
        }
    }

}
