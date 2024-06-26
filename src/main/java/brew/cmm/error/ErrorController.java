package brew.cmm.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorCode = "/";

        if(status != null) {
            int code = Integer.valueOf(status.toString());

            if(code == HttpStatus.NOT_FOUND.value()) {
                errorCode = "/code404";
            }

            if(code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorCode = "/code500";
            }
        }
        return errorCode;
    }
}
