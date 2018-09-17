package cn.inctech.app.sys.bean;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.inctech.app.common.dao.jdbc.sql.JdbcQueryBean;
import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.common.util.GeneralUtil;

@Component
public class SysServiceBean {

	boolean isExistUserForMobile(String mobile) {
		boolean r=false;
		Object[] param={mobile};
		Long count=(Long)jqb.querySingle(sql_query_user_by_mobile, param).get("count");
		if(count>0)
			r=true;
		return r;
	}
	
	public String sendSmsCode(String mobile) {
		if(isExistUserForMobile(mobile))
			throw new AppSysException(MOBILE_ALREADY_EXIST);
		String code="1111";//gutil.send_sms(mobile);
		return code;
	}
	
	final static String MOBILE_ALREADY_EXIST="该手机号已经注册";
	@Value("${webapp.security.jdbc.sql_query_user_by_mobile}") String sql_query_user_by_mobile;
	@Resource JdbcQueryBean jqb;
	@Resource GeneralUtil gutil;
}
