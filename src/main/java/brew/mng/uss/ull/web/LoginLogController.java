package brew.mng.uss.ull.web;

import brew.mng.uss.ull.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mng/uss/ull")
@RequiredArgsConstructor
public class LoginLogController {

    private final LoginLogService loginLogService;

    @RequestMapping("/selectLoginLogListVw.do")
    public String selectLoginLogListVw() {
        return "/mng/uss/ull/selectLoginLogListVw";
    }
}
