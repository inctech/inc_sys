package cn.inctech.app.common.mvc;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aliyuncs.utils.StringUtils;

import static cn.inctech.app.sys.param.SysParam.*;

public class SysMvcInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		if(currentUser.get(CU_KEY_USERNAME)!=null) {
			process_request(request);
		}
		return super.preHandle(request, response, handler);
	}
	
	void process_request(HttpServletRequest request) {
		String [] req_params= {CR_CURRENT_PAGE,CR_PAGE_SIEZ};
		String val=null;
		for(String k:req_params) {
			if(StringUtils.isNotEmpty(val=request.getParameter(CR_CURRENT_PAGE)))
				currentUser.put(k, val);
		}
	}
	
	@Resource Map<String,Object> currentUser;
}
