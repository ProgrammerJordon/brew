package brew.brew_common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StringUtil {

    /**
     * 조사 유형(postPositionType)
     */
    @Getter
    @AllArgsConstructor
    public enum postPositionType {
        TYPE1("은", "는"),
        TYPE2("이", "가"),
        TYPE3("을", "를");

        private final String firstValue;
        private final String secondValue;
    }

    /**
     * 입력한 값의 한글 조사를 붙여 리턴한다.
     * @param value
     * @param postPositionType
     * @return
     */
    public static String attachPostPosition(String value, postPositionType postPositionType) {
        if(isEmpty(value) == true || value.length() < 1) {
            return "";
        }
        char lastWord = value.charAt(value.length() - 1);

        // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if(lastWord < 0xAC00 || lastWord > 0xD7A3) {
            return value;
        }
        String addValue = (lastWord - 0xAC00) % 28 > 0 ? postPositionType.getFirstValue() : postPositionType.getSecondValue();
        return value + addValue;
    }

    /**
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return str == null || (str instanceof String) == false || str.toString().length() == 0;
    }

    /**
     * String이 비었거나("") null이면, 대체문자열, 아니면 원본 문자열을 리턴한다.
     * @param src
     * @param alt
     * @return
     */
    public static String emptyConvert(Object src, String alt) {
        return isEmpty(src) ? alt : src.toString();
    }
}
