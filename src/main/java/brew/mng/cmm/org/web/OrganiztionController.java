package brew.mng.cmm.org.web;

import brew.mng.cmm.org.service.OrganiztionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/cmm/org")
@RequiredArgsConstructor
public class OrganiztionController {

    private final OrganiztionService organiztionService;

    @RequestMapping("/selectOrganiztionListVw.do")
    public String selectOrganiztionListVw() {
        return "/mng/cmm/org/selectOrganiztionListVw";
    }
}
