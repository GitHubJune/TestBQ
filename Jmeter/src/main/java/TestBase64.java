

import sun.misc.BASE64Encoder;
public class TestBase64 {

    public String getBase64(String str) {
    	byte[] result = str.getBytes();
    	
    	return new BASE64Encoder().encode(result);
    	
    }

}