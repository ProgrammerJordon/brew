package brew.cmm.util;

import org.springframework.stereotype.Component;

@Component
public class BrewCommonUtil {

    public static boolean isEmpty(Object str) {
        return str == null || (str instanceof String) == false || str.toString().length() == 0;
    }

    public static String emptyConvert(Object src, String alt) {
        return isEmpty(src) ? alt : src.toString();
    }

}
