package brew.mng.dtm.mdm.web;

import brew.cmm.service.fms.service.FileMngService;
import brew.cmm.service.fms.service.FileVO;
import brew.mng.dtm.mdm.service.Metadata;
import brew.mng.dtm.mdm.service.MetadataService;
import brew.mng.dtm.mdm.service.MetadataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/mng/dtm/mdm")
@RequiredArgsConstructor
public class MetadataController {

    private final FileMngService fileMngService;

    private final MetadataService metadataService;

    /**
     * 메타데이터관리 목록 화면
     * @return
     */
    @RequestMapping("/selectMetadataListVw.do")
    public String selectMetadataListVw() {
        return "/mng/dtm/mdm/selectMetadataListVw";
    }

    @RequestMapping("/selectMetadataList.do")
    @ResponseBody
    public Metadata selectMetadataList(@RequestBody MetadataVO vo) {
        return metadataService.selectMetadataList(vo);
    }
    /**
     * 메타데이터관리 등록 화면
     * @return
     */
    @RequestMapping("/insertMetadataVw.do")
    public String insertMetadataVw() {
        return "/mng/dtm/mdm/insertMetadataVw";
    }

    @RequestMapping("/insertMetadata.do")
    @ResponseBody
    public Metadata insertMetadata(@RequestBody MetadataVO vo) {
        return metadataService.insertMetadata(vo);
    }

    /**
     * 메타데이터관리 상세화면
     * @return
     */
    @RequestMapping("/selectMetadataDtlsVw.do")
    public String selectMetadataDtlsVw(@ModelAttribute MetadataVO vo, Model model, FileVO fvo) throws Exception {

        Metadata rs = metadataService.selectMetadataDtls(vo);
        fvo.setAtchFileId(rs.getMetadataVO().getAtchFileId());
        List<FileVO> fvoList = fileMngService.selectFileInfs(fvo);

        model.addAttribute("mdmId", vo.getMdmId());
        model.addAttribute("atchFileId", rs.getMetadataVO().getAtchFileId());
        model.addAttribute("fileList", fvoList);

        return "/mng/dtm/mdm/selectMetadataDtlsVw";
    }

    @RequestMapping("/selectMetadataDtls.do")
    @ResponseBody
    public Metadata selectMetadataDtls(@RequestBody MetadataVO vo) {
        return metadataService.selectMetadataDtls(vo);
    }

    @RequestMapping("/updateMetadata.do")
    @ResponseBody
    public Metadata updateMetadata(@RequestBody MetadataVO vo) {

        String atchFileID = vo.getAtchFileId();

        if(!vo.getAtchFileId().equals("")) {
            vo.setAtchFileId(atchFileID);
        }else {
            vo.setAtchFileId(null);
        }

        return metadataService.updateMetadata(vo);
    }

    @RequestMapping("/deleteMetadata.do")
    @ResponseBody
    public Metadata deleteMetadata(@RequestBody MetadataVO vo) throws Exception {
        return metadataService.deleteMetadata(vo);
    }
}
