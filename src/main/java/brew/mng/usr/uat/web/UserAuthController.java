package brew.mng.usr.uat.web;

import brew.mng.usr.uat.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/usr/uat")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService userAuthService;

    @RequestMapping("/selectUserAuthListVw.do")
    public String selectUserAuthListVw() {
        return "/mng/usr/uat/selectUserAuthListVw";
    }
}
