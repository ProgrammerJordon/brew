package brew.mng.usr.usm.web;

import brew.mng.usr.usm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/usr/usm")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @RequestMapping("/selectUserMngListVw.do")
    public String selectUserMngListVw() {
        return "/mng/usr/usm/selectUserMngListVw";
    }
}
