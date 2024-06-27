package brew.mng.usr.uex.web;

import brew.mng.usr.uex.service.UserExit;
import brew.mng.usr.uex.service.UserExitService;
import brew.mng.usr.uex.service.UserExitVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/usr/uex")
@RequiredArgsConstructor
public class UserExitController {

    private final UserExitService userExitService;

    @RequestMapping("/selectUserExitListVw.do")
    public String selectUserExitListVw() {
        return "/mng/usr/uex/selectUserExitListVw";
    }

    @RequestMapping("/selectUserExitList.do")
    @ResponseBody
    public UserExit selectUserExitList(@RequestBody UserExitVO vo) {
        return userExitService.selectUserExitList(vo);
    }

    @RequestMapping("/updateUserExit.do")
    @ResponseBody
    public UserExit updateUserExit(@RequestBody UserExitVO vo) {
        return userExitService.updateUserExit(vo);
    }
}
