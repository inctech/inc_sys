package cn.inctech.app;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

import cn.inctech.app.common.sms.aliyun.AliyunSms;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartAppTests {

	@Test
	public void pass() {
		String p="A12345671234567891234567891234567891234567891234567891234567891234567890000000890";
		String r=null;
		r=bcrPasswordEncoder.encode(p);
		System.out.println(r+":"+r.length());
		p="a";
		r=bcrPasswordEncoder.encode(p);
		System.out.println(r+":"+r.length());
		p="helloworld";
		r=bcrPasswordEncoder.encode(p);
		System.out.println(r+":"+r.length());
	}
	
	
	public void send_sms() throws ClientException {
		String phoneNumber = "17615182526";
	    String randomNum = createRandomNum(6);
	    String jsonContent = "{\"code\":\"" + randomNum + "\"}";

	    Map<String, String> paramMap = new HashMap<>();
	    paramMap.put("PhoneNumbers", phoneNumber);
	    paramMap.put("SignName", "杨薇");
	    paramMap.put("TemplateCode", "SMS_142090353");
	    paramMap.put("TemplateParam", jsonContent);
	    SendSmsResponse sendSmsResponse = sms.sendSms(paramMap);
	    System.out.println(sendSmsResponse.getBizId()+":"+sendSmsResponse.getCode()+":"+sendSmsResponse.getMessage());
	    if(!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
	        if(sendSmsResponse.getCode() == null) {
	            //这里可以抛出自定义异常
	        }
	        if(!sendSmsResponse.getCode().equals("OK")) {
	             //这里可以抛出自定义异常
	        }
	    }
	}

	public static String createRandomNum(int num){
	    String randomNumStr = "";
	    for(int i = 0; i < num;i ++){
	        int randomNum = (int)(Math.random() * 10);
	        randomNumStr += randomNum;
	    }
	    return randomNumStr;
	}
	
	@Resource PasswordEncoder bcrPasswordEncoder;
	@Resource AliyunSms sms;
}
