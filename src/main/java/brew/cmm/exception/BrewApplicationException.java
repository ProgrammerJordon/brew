package brew.cmm.exception;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewMessageUtil;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;

@Getter
@SuppressWarnings("serial")
public class BrewApplicationException extends RuntimeException implements ApplicationContextAware {

    private static ApplicationContext context;

    private HttpStatus httpStatusCode;
    private String message;

    public BrewApplicationException() {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = getBrewMessageUtil().getMessage(BrewProperties.getProperty("fail.common.msg"));
    }

    public BrewApplicationException(String message) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = getBrewMessageUtil().getMessage(message);
    }

    public BrewApplicationException(String messageCode, Object[] args) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = getBrewMessageUtil().getMessage(messageCode, args);
    }

    public BrewApplicationException(String message, HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    private BrewMessageUtil getBrewMessageUtil() {
        return context.getBean(BrewMessageUtil.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
