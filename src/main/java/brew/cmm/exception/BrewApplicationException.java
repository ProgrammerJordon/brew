package brew.cmm.exception;

import brew.cmm.util.BrewMessageUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Getter
@SuppressWarnings("serial")
public class BrewApplicationException extends RuntimeException {

    private BrewMessageUtil brewMessageUtil;
    private HttpStatus httpStatusCode;
    private String message;

    public BrewApplicationException() {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        if(this.message == null) {
            this.message = brewMessageUtil.getMessage("fail.common.msg");
        }
    }

    public BrewApplicationException(String message) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = brewMessageUtil.getMessage(message);
    }

    public BrewApplicationException(String messageCode, Object[] args) {
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = brewMessageUtil.getMessage(messageCode, args);
    }

    public BrewApplicationException(String message, HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
