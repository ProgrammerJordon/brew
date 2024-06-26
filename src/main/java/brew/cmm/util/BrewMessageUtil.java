package brew.cmm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrewMessageUtil {
    private static BrewMessageSource brewMessageSource;

    @Autowired
    private BrewMessageUtil(BrewMessageSource brewMessageSource) {
        BrewMessageUtil.brewMessageSource = brewMessageSource;
    }

    public static String getMessage(String code) {
        return brewMessageSource.getMessage(code);
    }

    public static String getMessage(String code, Object[] args) {
        return brewMessageSource.getMessageArgs(code, args);
    }

}
