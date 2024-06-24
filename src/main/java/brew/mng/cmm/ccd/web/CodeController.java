package brew.mng.cmm.ccd.web;

import brew.mng.cmm.ccd.service.Code;
import brew.mng.cmm.ccd.service.CodeService;
import brew.mng.cmm.ccd.service.CodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/cmm/ccd")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @RequestMapping("/selectCodeListVw.do")
    public String selectCodeListVw() {
        return "/mng/cmm/ccd/selectCodeListVw";
    }

    @RequestMapping("/selectCodeList.do")
    @ResponseBody
    public Code selectCodeList(@RequestBody CodeVO vo) {
        return codeService.selectCodeList(vo);
    }

    @RequestMapping("/selectCodeInsertVw.do")
    public String selectCodeInsertVw() {
        return "/mng/cmm/ccd/selectCodeInsertVw";
    }

    @RequestMapping("/insertCode.do")
    @ResponseBody
    public Code insertCode(@RequestBody CodeVO vo) {
        return codeService.insertCode(vo);
    }

    @RequestMapping("/selectCodeDtlsVw.do")
    public String selectCodeDtlsVw(@RequestParam(name = "codeId", required = false) String codeId,
                                   Model model) {
        model.addAttribute("codeId", codeId);
        return "/mng/cmm/ccd/selectCodeDtlsVw";
    }

    @RequestMapping("/selectCode.do")
    @ResponseBody
    public Code selectCode(@RequestBody CodeVO vo) {
        return codeService.selectCode(vo);
    }

    @RequestMapping("/selectCodeUpdateVw.do")
    public String selectCodeUpdateVw(@RequestParam(name = "codeId", required = false) String codeId,
                                     Model model) {
        model.addAttribute("codeId", codeId);
        return "/mng/cmm/ccd/selectCodeUpdateVw";
    }

    @RequestMapping("/updateCode.do")
    @ResponseBody
    public Code updateCode(@RequestBody CodeVO vo) {
        return codeService.updateCode(vo);
    }

    @RequestMapping("/deleteCode.do")
    @ResponseBody
    public Code deleteCode(@RequestBody CodeVO vo) {
        return codeService.deleteCode(vo);
    }

    @RequestMapping("/selectCodeDtlsInsertVw.do")
    public String selectCodeDtlsInsertVw(@RequestParam(name = "codeId", required = false) String codeId,
                                         Model model) {
        model.addAttribute("codeId", codeId);
        return "/mng/cmm/ccd/selectCodeDtlsInsertVw";
    }

    @RequestMapping("/insertCodeDtls.do")
    @ResponseBody
    public Code insertCodeDtls(@RequestBody CodeVO vo) {
        return codeService.insertCodeDtls(vo);
    }

    @RequestMapping("/selectCodeDtlsList.do")
    @ResponseBody
    public Code selectCodeDtlsList(@RequestBody CodeVO vo) {
        return codeService.selectCodeDtlsList(vo);
    }

    @RequestMapping("/selectCodeDtlsUpadteVw.do")
    public String selectCodeDtlsUpadteVw(@RequestParam(name = "codeId", required = false) String codeId,
                                         @RequestParam(name = "code", required = false) String code,
                                         Model model) {
        model.addAttribute("codeId", codeId);
        model.addAttribute("code", code);
        return "/mng/cmm/ccd/selectCodeDtlsUpadteVw";
    }

    @RequestMapping("/selectCodedtls.do")
    @ResponseBody
    public Code selectCodedtls(@RequestBody CodeVO vo) {
        return codeService.selectCodedtls(vo);
    }

    @RequestMapping("/updateCodedtls.do")
    @ResponseBody
    public Code updateCodedtls(@RequestBody CodeVO vo) {
        return codeService.updateCodedtls(vo);
    }

    @RequestMapping("/deleteCodedtls.do")
    @ResponseBody
    public Code deleteCodedtls(@RequestBody CodeVO vo) {
        return codeService.deleteCodedtls(vo);
    }
}
