package brew.mng.cmm.org.web;

import brew.mng.cmm.org.service.Organization;
import brew.mng.cmm.org.service.OrganizationService;
import brew.mng.cmm.org.service.OrganizationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/cmm/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @RequestMapping("/selectOrganizationListVw.do")
    public String selectOrganizationListVw() {
        return "/mng/cmm/org/selectOrganizationListVw";
    }

    @RequestMapping("/selectOrganizationInsertVw.do")
    public String selectOrganizationInsertVw() {
        return "/mng/cmm/org/selectOrganizationInsertVw";
    }

    @RequestMapping("/insertOranization.do")
    @ResponseBody
    public Organization insertOranization(@RequestBody OrganizationVO vo) {
        return organizationService.insertOranization(vo);
    }
}
