package brew.svc.csc.cst.web;

import brew.svc.csc.cst.service.Consult;
import brew.svc.csc.cst.service.ConsultService;
import brew.svc.csc.cst.service.ConsultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestMapping("/svc/csc/cst")
@RequiredArgsConstructor
public class ConsultController {

    private final ConsultService consultService;

    @RequestMapping("/selectConsultListVw.do")
    public String selectConsultListVw() {

        return "/svc/csc/cst/selectConsultListVw";
    }

    @RequestMapping("/selectConsultInsertVw.do")
    public String selectConsultInsertVw() {

        return "/svc/csc/cst/selectConsultInsertVw";
    }

    @RequestMapping("/insertConsult.do")
    @ResponseBody // 화면에 JSON 값을 뿌려
    public Consult insertConsult(@RequestBody ConsultVO vo) {
        Consult result = consultService.insertConsult(vo);

        return result;
    }

    @RequestMapping("/selectConsultList.do")
    @ResponseBody
    public Consult selectConsultList(@RequestBody ConsultVO vo) {
        return consultService.selectConsultList(vo);
    }

    @RequestMapping("/selectConsultDtlsVw.do")
    public String selectConsultDtlsVw(@RequestParam(name = "sn") int sn,
                                      ConsultVO vo,
                                      Model model) {
        vo.setSn(sn);
        model.addAttribute("vo", vo);

        return "/svc/csc/cst/selectConsultDtlsVw";
    }

    @RequestMapping("/selectConsultDtVw.do")
    @ResponseBody
    public Consult selectConsultDtVw(@RequestBody ConsultVO vo) {
        return consultService.selectConsultDtVw(vo);
    }

    @RequestMapping("/updateConsultVw.do")
    public String updateConsultVw(@RequestParam(name = "sn") int sn,
                                  ConsultVO vo,
                                  Model model) {
        vo.setSn(sn);
        model.addAttribute("vo", vo);

        return "/svc/csc/cst/updateConsultVw";
    }

    @RequestMapping("/updateConsult.do")
    @ResponseBody
    public Consult updateConsult(@RequestBody ConsultVO vo) {
        return consultService.updateConsult(vo);
    }

    @RequestMapping("deleteConsult.do")
    @ResponseBody
    public Consult deleteConsult(@RequestBody ConsultVO vo) {
        return consultService.deleteConsult(vo);
    }

}
