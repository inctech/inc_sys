package cn.inctech.app.common.util;

import javax.annotation.Resource;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.aliyuncs.utils.StringUtils;

/**
 * @author Administrator
 * 常用工具类
 * 不使用static method
 */
@Component()
public class GeneralUtil {

	public String buildBcrPass(String plain) {
		if(StringUtils.isEmpty(plain)||plain.startsWith("$2a$"))
			return plain;
		return bcrPasswordEncoder.encode(plain);
	}
	
	@Resource PasswordEncoder bcrPasswordEncoder;
}
