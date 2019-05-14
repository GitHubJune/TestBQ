package Jmeter.Jmeter;

import org.apache.commons.codec.digest.DigestUtils;

public class TestMD5 {

	public String getMD5(String str) {
		String result=DigestUtils.md5Hex(str);
        return result;
	}
	
}
