package cn.inctech.app.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.utils.StringUtils;

import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.common.sms.aliyun.AliyunSms;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * 常用工具类
 * 不使用static method
 */
@Slf4j
@Component()
public class GeneralUtil {

	public String send_sms(String desc_mobile) {
		String phoneNumber = desc_mobile;//"17615182526";
	    String randomNum = randomNum4();
	    String jsonContent = "{\"code\":\"" + randomNum + "\"}";

	    Map<String, String> paramMap = new HashMap<>();
	    paramMap.put("PhoneNumbers", phoneNumber);
	    paramMap.put("SignName", sign_name);
	    paramMap.put("TemplateCode", template_code);
	    paramMap.put("TemplateParam", jsonContent);
	    SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = sms.sendSms(paramMap);
			log.debug(sendSmsResponse.getBizId()+":"+sendSmsResponse.getCode()+":"+sendSmsResponse.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
			throw new AppSysException(SMS_SEND_CODE_ERROR);
		}
	    
	    return randomNum;
	}
	
	public String randomNum4() {
		return randomNum(4);
	}
	
	public String randomNum6() {
		return randomNum(6);
	}
	
	/**
	 * 生成指定长度的随机数
	 * @param len
	 * @return
	 */
	public String randomNum(int len) {
		if(len<1)
			throw new AppSysException("length of random value is too short!");
		String s="0123456789";
		final int bound=10;
		int suffix=-1;
		StringBuilder t=new StringBuilder();
		for(int i=0;i<len;i++) {
			suffix=random.nextInt(bound);
			t.append(s.charAt(suffix));
		}
		return t.toString();
	}
	
	/**
	 * 判断字符串是否使用BCR方式加密
	 * @param plain
	 * @return
	 */
	public String buildBcrPass(String plain) {
		if(StringUtils.isEmpty(plain)||plain.startsWith("$2a$"))
			return plain;
		return bcrPasswordEncoder.encode(plain);
	}
	
	final static String SMS_SEND_CODE_ERROR="向短信服务器中心发送验证码消息失败";
	Random random=new Random();
	
	@Value("${webapp.sms.sign_name}") String sign_name;
	@Value("${webapp.sms.template_code}") String template_code;
	
	@Resource PasswordEncoder bcrPasswordEncoder;
	@Resource AliyunSms sms;
	
	public static void main(String[] args) {
		GeneralUtil u=new GeneralUtil();
		for(int i=0;i<20;i++)
			System.out.println(u.randomNum(4));
	}
	
}
