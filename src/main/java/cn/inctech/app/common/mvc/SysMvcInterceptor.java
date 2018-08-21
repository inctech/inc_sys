package cn.inctech.app.common.mvc;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import static cn.inctech.app.sys.param.SysParam.*;

public class SysMvcInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		if(!currentUser.containsKey(CU_KEY_USERNAME)) {
			
		}
		return super.preHandle(request, response, handler);
	}
	
	@Resource Map<String,Object> currentUser;
}
