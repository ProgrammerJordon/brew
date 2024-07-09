package brew.cmm.exception;

import brew.cmm.service.ppt.BrewProperties;
import brew.cmm.util.BrewMessageUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@SuppressWarnings("serial")
public class BrewApplicationException extends RuntimeException {

    private HttpStatus httpStatusCode;
    private String message;

    public BrewApplicationException(BrewMessageUtil brewMessageUtil) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = brewMessageUtil.getMessage(BrewProperties.getProperty("fail.common.msg"));
    }

    public BrewApplicationException(String message, BrewMessageUtil brewMessageUtil) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = brewMessageUtil.getMessage(message);
    }

    public BrewApplicationException(String messageCode, Object[] args, BrewMessageUtil brewMessageUtil) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = brewMessageUtil.getMessage(messageCode, args);
    }

    public BrewApplicationException(String message, HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
