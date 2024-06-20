package brew.cmm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrewBrowerUtil {

    public static final String FIREFOX = "Firefox";
    public static final String SAFARI = "Safari";
    public static final String CHROME = "Chrome";
    public static final String OPERA = "Opera";
    public static final String MSIE = "MSIE";
    public static final String EDGE = "Edge";
    public static final String OTHER = "Other";
    public static final String TYPEKEY = "type";
    public static final String VERSIONKEY = "version";

    public static HashMap<String,String> getBrowser(String userAgent) {

        HashMap<String,String> result = new HashMap<String,String>();
        Pattern pattern = null;
        Matcher matcher = null;
        //System.out.println("=====>>>>> userAgent = "+userAgent);

        pattern = Pattern.compile("MSIE ([0-9]{1,2}.[0-9])");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,MSIE);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        if (userAgent.indexOf("Trident/7.0") > -1) {
            result.put(TYPEKEY,MSIE);
            result.put(VERSIONKEY,"11.0");
            return result;
        }

        pattern = Pattern.compile("Edge/([0-9]{1,3}.[0-9]{1,5})");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,EDGE);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        pattern = Pattern.compile("Firefox/([0-9]{1,3}.[0-9]{1,3})");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,FIREFOX);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        pattern = Pattern.compile("OPR/([0-9]{1,3}.[0-9]{1,3})");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,OPERA);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        pattern = Pattern.compile("Chrome/([0-9]{1,3}.[0-9]{1,3})");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,CHROME);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        pattern = Pattern.compile("Version/([0-9]{1,2}.[0-9]{1,3})");
        matcher = pattern.matcher(userAgent);
        if (matcher.find())
        {
            result.put(TYPEKEY,SAFARI);
            result.put(VERSIONKEY,matcher.group(1));
            return result;
        }

        result.put(TYPEKEY,OTHER);
        result.put(VERSIONKEY,"0.0");
        return result;
    }

    public static String getDisposition(String filename, String userAgent, String charSet) throws UnsupportedEncodingException {

        String encodedFilename = null;
        HashMap<String,String> result = BrewBrowerUtil.getBrowser(userAgent);
        float version = Float.parseFloat(result.get(BrewBrowerUtil.VERSIONKEY));

        if ( BrewBrowerUtil.MSIE.equals(result.get(BrewBrowerUtil.TYPEKEY)) && version <= 8.0f ) {
            encodedFilename = "Content-Disposition: attachment; filename="+ URLEncoder.encode(filename, charSet).replaceAll("\\+", "%20");
        } else if ( BrewBrowerUtil.OTHER.equals(result.get(BrewBrowerUtil.TYPEKEY)) ) {
            throw new RuntimeException("Not supported browser");
        } else {
            encodedFilename = "attachment; filename*="+charSet+"''"+URLEncoder.encode(filename, charSet);
        }

        return encodedFilename;
    }


}
