package brew.mng.usr.usm.web;

import brew.mng.usr.usm.service.User;
import brew.mng.usr.usm.service.UserService;
import brew.mng.usr.usm.service.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/selectUserMngDtlsVw.do")
    public String selectUserMngDtlsVw(@RequestParam (name = "userSn") String userSn,
                                      Model model) {
        model.addAttribute("userSn", userSn);
        return "/mng/usr/usm/selectUserMngDtlsVw";
    }

    @RequestMapping("/selectUserMngDtls.do")
    @ResponseBody
    public User selectUserMngDtls(@RequestBody UserVO vo) {
        return userService.selectUserMngDtls(vo);
    }

    @RequestMapping("/updateUserMng.do")
    @ResponseBody
    public User updateUserMng(@RequestBody UserVO vo) {
        return userService.updateUserMng(vo);
    }

    @RequestMapping("/exitUserMng.do")
    @ResponseBody
    public User exitUserMng(@RequestBody UserVO vo) {
        return userService.exitUserMng(vo);
    }
}
