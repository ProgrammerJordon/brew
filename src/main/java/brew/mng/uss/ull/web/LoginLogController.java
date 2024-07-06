package brew.mng.uss.ull.web;

import brew.mng.uss.ull.service.LogInLogVO;
import brew.mng.uss.ull.service.LoginLog;
import brew.mng.uss.ull.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mng/uss/ull")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    @RequestMapping("/selectLoginLogListVw.do")
    public String selectLoginLogListVw() {
        return "/mng/uss/ull/selectLoginLogListVw";
    }

    @RequestMapping("/selectLoginLogList.do")
    @ResponseBody
    public LoginLog selectLoginLogList(@RequestBody LogInLogVO vo) {
        return loginLogService.selectLoginLogList(vo);
    }
}
