package cn.inctech.app.sys.bean;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.inctech.app.common.dao.jdbc.sql.JdbcQueryBean;
import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.common.util.GeneralUtil;
import lombok.Data;

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
		//String code=gutil.send_sms(mobile); recovery line in real env 
		String code="1111";//gutil.send_sms(mobile);
		return code;
	}
	
	/**
	 *[{1:man},{2:woman}] 
	*/
	public List<Map<String,Object>> getCode(String property){
		return jqb.queryMulti(SQL_QUERY_CODE_BY_PROPERTY, new Object[] {property});
	}
	
	/**
	 *[
	 *	{key1:[{1:man},{2:woman}]},
	 *	{key2:[{1:dalian},{2:nanjing}]}
	 *] 
	*/
	public Map<String,List</*Map<String,Object>*/Code>> getAllCode(){
		return jqb.queryMulti(SQL_QUERY_ALL_CODE, new Object[] {}).stream().map(
				e->new Code(e.get("property")+"",e.get("code")+"",e.get("label")+"")).collect(Collectors.groupingBy(Code::getProperty));
	}
	
	@Data
	class Code{
		Code(String p,String c,String l){
			this.property=p;
			this.code=c;
			this.label=l;
		}
		String property;
		String code;
		String label;
	}
	
	final static String MOBILE_ALREADY_EXIST="该手机号已经注册";
	
	final static String SQL_QUERY_ALL_CODE="select fname property,fvalue code,fcontent label from s_code";
	final static String SQL_QUERY_CODE_BY_PROPERTY=SQL_QUERY_ALL_CODE+" where fname=?";
	final static String SQL_QUERY_DISTINCT_CODE_KEY="select distinct fname property from s_code";
	
	@Value("${webapp.security.jdbc.sql_query_user_by_mobile}") String sql_query_user_by_mobile;
	@Resource JdbcQueryBean jqb;
	@Resource GeneralUtil gutil;
}
