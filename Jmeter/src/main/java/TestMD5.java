

import org.apache.commons.codec.digest.DigestUtils;

public class TestMD5 {

	public String getMD5(String str) {
		String result=DigestUtils.md5Hex(str);
        return result;
	}
	
	public static void main(String[] args) {
		TestMD5 md = new TestMD5();
		String temp = md.getMD5("helloworldappkey12345678methodbqapi_testpartner_idpandoratimestamp1451620800version1.0eyJpZCI6MSwiZnVuYyI6InRlc3QifQ==helloworld");
		System.out.println(temp);
	}
}
