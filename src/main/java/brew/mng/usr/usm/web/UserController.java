package brew.mng.usr.usm.web;

import brew.mng.usr.usm.service.User;
import brew.mng.usr.usm.service.UserService;
import brew.mng.usr.usm.service.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/usr/usm")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/selectUserMngListVw.do")
    public String selectUserMngListVw() {
        return "/mng/usr/usm/selectUserMngListVw";
    }

    @RequestMapping("/selectUserMngList.do")
    @ResponseBody
    public User selectUserMngList(@RequestBody UserVO vo) {
        return userService.selectUserMngList(vo);
    }
}
