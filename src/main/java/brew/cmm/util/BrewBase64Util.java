package brew.cmm.util;

public class BrewBase64Util {
	
	public static String encodeBase64String(byte[] bytes) throws  Exception {
		
		String encoded = "";
		
		if(bytes != null) {
			encoded = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
		}
		
		return encoded;	
	}

	public static byte[] decodeBase64(String data) throws  Exception {
		
		byte[] decode = null;
		
		if(data != null) {
			decode = org.apache.commons.codec.binary.Base64.decodeBase64(data);
		}
		
		return decode;	
	}
	
}
