package brew.cmm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrewMessageUtil {
    private final BrewMessageSource brewMessageSource;

    @Autowired
    public BrewMessageUtil(BrewMessageSource brewMessageSource) {
        this.brewMessageSource = brewMessageSource;
    }

    public String getMessage(String code) {
        return brewMessageSource.getMessage(code);
    }

    public String getMessage(String code, Object[] args) {
        return brewMessageSource.getMessageArgs(code, args);
    }

}
