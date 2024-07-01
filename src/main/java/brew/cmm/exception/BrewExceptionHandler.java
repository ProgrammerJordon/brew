package brew.cmm.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class BrewExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected String handleException(HttpServletRequest request, HttpServletResponse response, Model model, Exception e) {

        log.error("** handleException *****************************************");
        e.printStackTrace();
        log.error("************************************************************");

        if (isAjaxRequest(request) == false) {
            BrewApplicationException applicationException = new BrewApplicationException();
            model.addAttribute("errorCode", applicationException.getHttpStatusCode());
            model.addAttribute("errorMessage", applicationException.getMessage());
            return "/error";
        } else {
            response.setStatus(new BrewApplicationException().getHttpStatusCode().value());
            model.addAttribute("errorMessage", new BrewApplicationException().getMessage());

            return "jsonView";
        }
    }

    @ExceptionHandler(value = {BrewApplicationException.class})
    protected String handleCustomException(HttpServletRequest request, HttpServletResponse response, Model model, BrewApplicationException e) {

        log.error("** handleCustomException ***********************************");
        log.error(e.getMessage());
        e.printStackTrace();
        log.error("************************************************************");

        if (isAjaxRequest(request) == false) {
            model.addAttribute("errorCode", e.getHttpStatusCode() != null ? e.getHttpStatusCode().value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
            model.addAttribute("errorMessage", e.getMessage());
            return "/error";
        } else {
            response.setStatus(e.getHttpStatusCode().value());
            model.addAttribute("errorMessage", e.getMessage());

            return "jsonView";
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "true".equals(request.getHeader("AJAX"));
    }

}
