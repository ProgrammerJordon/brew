package brew.cmm.service.ppt;

import brew.cmm.util.BrewStringUtil;
import brew.cmm.util.BrewWebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BrewProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrewProperties.class);

    //파일구분자
    final static String FILE_SEPARATOR = System.getProperty("file.separator");

    //프로퍼티 파일의 물리적 위치
    //public static final String GLOBALS_PROPERTIES_FILE = System.getProperty("user.home") + FILE_SEPARATOR + "egovProps" +FILE_SEPARATOR + "common.properties";

    public static final String RELATIVE_PATH_PREFIX = BrewProperties.class.getResource("") == null ? ""
            : BrewProperties.class.getResource("").getPath().substring(0,
            BrewProperties.class.getResource("").getPath().lastIndexOf("com"));
    //public static final String RELATIVE_PATH_PREFIX = EgovProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(0,EgovProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath().indexOf("WEB-INF/classes/")+"WEB-INF/classes/".length())+"egovframework/";

    public static final String GLOBALS_PROPERTIES_FILE = RELATIVE_PATH_PREFIX + "brewProps" + FILE_SEPARATOR + System.getProperty("env") + FILE_SEPARATOR
            + "application.properties";

    public static final String COMMON_PROPERTIES_FILE = RELATIVE_PATH_PREFIX + "brewProps" + FILE_SEPARATOR + System.getProperty("env") + FILE_SEPARATOR
            + "application.properties";

    /**
     * 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다(Globals.java 전용)
     * @param keyName String
     * @return String
     */
    public static String getProperty(String keyName) {
        // 221116	김혜준	2022 시큐어코딩 조치
        LOGGER.debug("===>>> getProperty" + BrewStringUtil.isNullToString(BrewProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
        LOGGER.debug("getProperty : {} = {}", GLOBALS_PROPERTIES_FILE, keyName);

        return getPropertyValueByKey(GLOBALS_PROPERTIES_FILE, keyName);
    }

    public static String getCommonProperty(String keyName) {
        // 221116	김혜준	2022 시큐어코딩 조치
        LOGGER.debug("===>>> getProperty" + BrewStringUtil.isNullToString(BrewProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
        LOGGER.debug("getProperty : {} = {}", COMMON_PROPERTIES_FILE, keyName);

        return getPropertyValueByKey(COMMON_PROPERTIES_FILE, keyName);
    }

    /**
     * 인자로 주어진 문자열을 Key값으로 하는 상대경로 프로퍼티 값을 절대경로로 반환한다(Globals.java 전용)
     * @param keyName String
     * @return String
     */
    public static String getPathProperty(String keyName) {
        LOGGER.debug("getPathProperty : {} = {}", COMMON_PROPERTIES_FILE, keyName);

        return RELATIVE_PATH_PREFIX + "egovProps" + FILE_SEPARATOR + System.getProperty("env") + FILE_SEPARATOR + getCommonProperty(keyName);
    }

    /**
     * 주어진 파일에서 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다
     * @param fileName String
     * @return String
     */
    public static String getProperty(String fileName, String keyName) {
        return getPropertyValueByKey(fileName, keyName);
    }

    /**
     * 주어진 파일에서 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 상대 경로값을 절대 경로값으로 반환한다
     * @param fileName String
     * @return String
     */
    public static String getPathProperty(String fileName, String keyName) {
        return RELATIVE_PATH_PREFIX + "egovProps" + FILE_SEPARATOR  + System.getProperty("evn") + FILE_SEPARATOR + getProperty(fileName, keyName);
    }

    /**
     * 주어진 프로파일의 내용을 파싱하여 (key-value) 형태의 구조체 배열을 반환한다.
     * @param property String
     * @return ArrayList
     */
    public static ArrayList<Map<String, String>> loadPropertyFile(String property) {

        // key - value 형태로 된 배열 결과
        ArrayList<Map<String, String>> keyList = new ArrayList<Map<String, String>>();

        String src = property.replace("\\", FILE_SEPARATOR).replace("/", FILE_SEPARATOR);

        if (Files.exists(Paths.get(BrewWebUtil.filePathBlackList(src)))) { //2022.01 Potential Path Traversal
            Properties props = loadPropertiesFromFile(src);

            Enumeration<?> plist = props.propertyNames();
            if (plist != null) {
                while (plist.hasMoreElements()) {
                    Map<String, String> map = new HashMap<String, String>();
                    String key = (String)plist.nextElement();
                    map.put(key, props.getProperty(key));
                    keyList.add(map);
                }
            }
        }

        return keyList;
    }

    /**
     * 기본 Property 에서 Property Key로 Property value 받아온다.
     * @param keyName
     * @return
     */
    public static String getPropertyValueByKey(String keyName) {
        return getPropertyValueByKey(GLOBALS_PROPERTIES_FILE, keyName);
    }

    /**
     * Property 파일을 지정하여 Property Key로 Property value 받아온다.
     * @param fileName
     * @param keyName
     * @return
     */
    public static String getPropertyValueByKey(String fileName, String keyName) {
        String propertyValue = "";
        Properties props = loadPropertiesFromFile(fileName);

        if (props.containsKey(keyName)) {
            propertyValue = props.getProperty(keyName).trim();
        }

        return propertyValue;
    }

    /**
     * Property 파일패스로 Properties 객체를 리턴한다.
     * @param fileName
     * @return
     */
    private static Properties loadPropertiesFromFile(String fileName) {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(BrewWebUtil.filePathBlackList(fileName));
             BufferedInputStream bis = new BufferedInputStream(fis);) {
            props.load(bis);
        } catch (FileNotFoundException fne) {
            LOGGER.debug("Property file not found.", fne);
            throw new RuntimeException("Property file not found", fne);
        } catch (IOException ioe) {
            LOGGER.debug("Property file IO exception", ioe);
            throw new RuntimeException("Property file IO exception", ioe);
        }

        return props;
    }
}
