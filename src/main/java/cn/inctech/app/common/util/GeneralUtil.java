package cn.inctech.app.common.util;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aliyuncs.utils.StringUtils;

import cn.inctech.app.common.exception.AppSysException;

/**
 * @author Administrator
 * 常用工具类
 * 不使用static method
 */
@Component()
public class GeneralUtil {

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
	
	Random random=new Random();
	
	@Resource PasswordEncoder bcrPasswordEncoder;
	
	public static void main(String[] args) {
		GeneralUtil u=new GeneralUtil();
		for(int i=0;i<20;i++)
			System.out.println(u.randomNum(6));
	}
	
}
