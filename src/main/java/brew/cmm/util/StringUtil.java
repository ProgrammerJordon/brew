package brew.cmm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 문자열 유틸 클래스
 *
 * @author JHKIM
 */
public class StringUtil extends BrewStringUtil {
    
    /**
     * 조사 유형
     */
    @Getter
    @AllArgsConstructor
    public enum PositionType {
        /**
         * 은/는
         */
        TYPE1("은", "는"),
        /**
         * 이/가
         */
        TYPE2("이", "가"),
        /**
         * 을/를
         */
        TYPE3("을", "를");
        
        private final String firstValue;
        private final String secondValue;
    }
    
    /**
     * 입력한 값의 한글 조사를 붙여 리턴
     *
     * @param value        - 조사를 붙일 입력 값
     * @param positionType - 조사 유형
     * @return 입력한 값의 한글 조사가 조합된 문자열
     */
    public static String attachPosition(String value, PositionType positionType) {
        
        if (isEmpty(value) == true || value.length() < 1) {
            return EMPTY;
        }
        
        char lastWord = value.charAt(value.length() - 1);
        
        // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
        if (lastWord < 0xAC00 || lastWord > 0xD7A3) {
            return value;
        }
        
        String addValue = (lastWord - 0xAC00) % 28 > 0 ? positionType.getFirstValue() : positionType.getSecondValue();
        
        return value + addValue;
    }
    
    /**
     * String이 비었거나("") 혹은 null 인지 검증한다.
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isEmpty(Object str) {
        return str == null || (str instanceof String) == false || str.toString().length() == 0;
    }
    
    /**
     * String이 비었거나("") 널이면, 대체문자열, 아니면 원본 문자열 리턴
     *
     * @param src - 체크 대상 스트링오브젝트이며 null을 허용함
     * @param alt - 대상 스트링오브젝트가 널이거나 값이 없을 경우 대체문자열
     * @return 입력받은 String 이 빈 문자열 또는 null인 경우, 대체문자열, 아니면 원본 문자열 리턴
     */
    public static String emptyConvert(Object src, String alt) {
        return isEmpty(src) ? alt : src.toString();
    }
    
}
