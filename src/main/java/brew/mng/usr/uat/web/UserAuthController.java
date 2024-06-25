package brew.mng.usr.uat.web;

import brew.mng.usr.uat.service.UserAuth;
import brew.mng.usr.uat.service.UserAuthService;
import brew.mng.usr.uat.service.UserAuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/usr/uat")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @RequestMapping("/selectUserAuthListVw.do")
    public String selectUserAuthListVw() {
        return "/mng/usr/uat/selectUserAuthListVw";
    }

    @RequestMapping("/selectUserAuthList.do")
    @ResponseBody
    public UserAuth selectUserAuthList(@RequestBody UserAuthVO vo) {
        return userAuthService.selectUserAuthList(vo);
    }

    @RequestMapping("/selectUserAuthDtlsVw.do")
    public String selectUserAuthDtlsVw(@RequestParam (name = "userSn") String userSn,
                                       Model model) {
        model.addAttribute("userSn", userSn);
        return "/mng/usr/uat/selectUserAuthDtlsVw";
    }
}
