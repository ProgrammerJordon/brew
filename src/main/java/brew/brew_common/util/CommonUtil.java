package brew.brew_common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class CommonUtil {

    protected static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    public static boolean isDataContained(List<String> list, String str) {

        boolean isContained = false;
        String tempUrl = null;

        for (String data : list) {

            char[] tempChar = data.toCharArray();

            if (tempChar[tempChar.length - 1] == '*') {
                tempUrl = String.valueOf(Arrays.copyOfRange(tempChar, 0, tempChar.length - 2));
            } else {
                tempUrl = data;
            }

            if (str.contains(tempUrl)) {
                isContained = true;
                break;
            }

            if ("**".equalsIgnoreCase(tempUrl)) {
                isContained = true;
                break;
            }

        }

        return isContained;

    }

    public static String getClientIp(HttpServletRequest request) {

        String clientIp = null;

        clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.length() == 0 || "unknown".equals(clientIp)) {
            clientIp = request.getHeader("Proxy-client-IP");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equals(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-client-IP");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equals(clientIp)) {
            clientIp = request.getHeader("HTTP-client-IP");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equals(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (clientIp == null || clientIp.length() == 0 || "unknown".equals(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;

    }

    /**
     * 외국인등록번호 검증번호 조회
     * <p>
     * 외국인등록번호 검증번호를 생성하여 넘겨준다
     * <p>
     *
     * @param regNo
     * @return
     * @throws Exception
     */
    public static String getRegNoCheckNo(String regNo) {

        String IdAdd = "234567892345"; // 외국인등록번호에 가산할값
        int totalId = 0; // 검증을 위한 변수
        int countNum = 0;
        int addNum = 0;

        for (int i = 0; i < 12; i++) {

            countNum = Character.getNumericValue(regNo.charAt(i));
            addNum = Character.getNumericValue(IdAdd.charAt(i));

            totalId += countNum * addNum;

        }

        totalId = 11 - (totalId % 11);

        if (totalId >= 10) {
            totalId -= 10;
        }

        totalId += 2;

        if (totalId >= 10) {
            totalId -= 10;
        }

        return String.valueOf(totalId);
    }

    /**
     * Host Name 얻기
     * <p>
     * 동작하는 서버의 hostname을 리턴한다.
     * <p>
     *
     * @return
     * @throws Exception
     */
    public static String getHostName() {
        String hostName = "";

        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostName = "";
            logger.warn(e.getMessage());
        }

        return hostName;
    }
}
