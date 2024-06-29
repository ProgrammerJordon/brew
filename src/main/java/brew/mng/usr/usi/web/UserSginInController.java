package brew.mng.usr.usi.web;

import brew.mng.usr.usi.service.UserSignIn;
import brew.mng.usr.usi.service.UserSignInService;
import brew.mng.usr.usi.service.UserSignInVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/usr/usi")
@RequiredArgsConstructor
public class UserSginInController {

    private final UserSignInService userSignInService;

    @RequestMapping("/selectUserSignInListVw.do")
    public String selectUserSignInListVw() {
        return "/mng/usr/usi/selectUserSignInListVw";
    }

    @RequestMapping("/selectUserSignInList.do")
    @ResponseBody
    public UserSignIn selectUserSignInList(@RequestBody UserSignInVO vo) {
        return userSignInService.selectUserSignInList(vo);
    }
}
