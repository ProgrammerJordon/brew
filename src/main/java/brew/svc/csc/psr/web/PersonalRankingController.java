package brew.svc.csc.psr.web;

import brew.svc.csc.psr.service.PersonalRanking;
import brew.svc.csc.psr.service.PersonalRankingService;
import brew.svc.csc.psr.service.PersonalRankingVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/svc/csc/psr")
@RequiredArgsConstructor
public class PersonalRankingController {

    private final PersonalRankingService personalRankingService;

    @RequestMapping("selectItListVw.do")
    public String selectItListVw() {
        return "/svc/csc/psr/selectItListVw";
    }

    @RequestMapping("selectPersonalRankingList.do")
    @ResponseBody
    public PersonalRanking selectPersonalRankingList(@RequestBody PersonalRankingVO vo) {
        return personalRankingService.selectPersonalRankingList(vo);
    }


}
