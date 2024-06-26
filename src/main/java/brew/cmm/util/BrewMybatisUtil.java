package brew.cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class BrewMybatisUtil {
    private static final Logger logger = LoggerFactory.getLogger(BrewMybatisUtil.class);

    /**
     * Empty 여부를 확인한다.
     * @param o Object
     * @return boolean
     * @exception IllegalArgumentException
     */
    public static boolean isEmpty(Object o) {

        if(o == null) return true;

        if(o instanceof String) {
            if(((String)o).length() == 0){
                return true;
            }
        } else if(o instanceof Collection) {
            if(((Collection)o).isEmpty()){
                return true;
            }
        } else if(o.getClass().isArray()) {
            if(Array.getLength(o) == 0){
                return true;
            }
        } else if(o instanceof Map) {
            if(((Map)o).isEmpty()){
                return true;
            }
        }else {
            return false;
        }

        return false;
    }

    /**
     * Not Empty 여부를 확인한다.
     * @param o Object
     * @return boolean
     * @exception IllegalArgumentException
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * Equal 여부를 확인한다.
     * @param obj Object, obj Object
     * @return boolean
     */

    public static boolean isEquals(Object obj, Object obj2){
        if(isEmpty(obj)) return false;

        if(obj instanceof String && obj2 instanceof String) {
            if( (String.valueOf(obj)).equals( String.valueOf(obj2) )){
                return true;
            }
        }else if(obj instanceof String && obj2 instanceof Character) {
            if( (String.valueOf(obj) ).equals( String.valueOf(obj2) )){
                return true;
            }
        }else if(obj instanceof String && obj2 instanceof Integer) {
            if( (String.valueOf(obj)).equals( String.valueOf((Integer)obj2) )){
                return true;
            }

        }else if(obj instanceof Integer && obj2 instanceof String) {
            if( (String.valueOf(obj2)).equals( String.valueOf((Integer)obj) )){
                return true;
            }
        } else if(obj instanceof Integer && obj instanceof Integer) {
            if((Integer)obj == (Integer)obj2){
                return true;
            }
        }

        return false;
    }

    /**
     * String의 Equal 여부를 확인한다.
     * @param obj Object, obj Object
     * @return boolean
     */
    public static boolean isEqualsStr(Object obj, String s){
        if(isEmpty(obj)) return false;

        if(s.equals(String.valueOf(obj))){
            return true;
        }
        return false;
    }
}
