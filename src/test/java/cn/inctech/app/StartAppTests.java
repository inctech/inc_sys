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

import cn.inctech.app.common.dao.jdbc.sql.JdbcQueryBean;
import cn.inctech.app.common.sms.aliyun.AliyunSms;
import cn.inctech.app.sys.bean.SysServiceBean;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StartAppTests {

	
	public void testJdbcQuery() {
		String sql="select mobile from v_user where userid=:userid";
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userid", "admin");
		String mobile=jqb.querySingle(sql, param).get("mobile")+"";
		System.out.println(mobile);
		sql="select count(*) count from v_user where userid=:userid";
		Object c=jqb.querySingle(sql, param).get("count");
		System.out.println(c+c.getClass().getName());
	}
	
	@Test
	public void testLambdaGroup() {
		log.info(ss.getAllCode()+"#####");
		log.info(ss.getCode("sex")+"****");
	}
	
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
		System.out.println(bcrPasswordEncoder.matches(p, r));
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
	
	@Resource SysServiceBean ss;
	@Resource PasswordEncoder bcrPasswordEncoder;
	@Resource AliyunSms sms;
	@Resource JdbcQueryBean jqb;
}
