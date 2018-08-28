package cn.inctech.app.common.mvc;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aliyuncs.utils.StringUtils;
import com.github.pagehelper.PageHelper;

import cn.inctech.app.sys.param.SysParam;

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
		String val=null;
		int cur_page=SysParam.DEFAULT_PAGE_INDEX;
		int page_size=SysParam.DEFAULT_PAGE_SIEZ;
		if(StringUtils.isNotEmpty(val=request.getParameter(CR_CURRENT_PAGE)))
			cur_page=Integer.parseInt(val);
		if(StringUtils.isNotEmpty(val=request.getParameter(CR_PAGE_SIEZ)))
			page_size=Integer.parseInt(val);
		currentUser.put(CR_CURRENT_PAGE, cur_page);
		currentUser.put(CR_PAGE_SIEZ, page_size);
		if(use_pageHelper) 
			PageHelper.startPage(cur_page, page_size);
	}
	
	@Value("${webapp.use_pageHelper}") boolean use_pageHelper=false;
	@Resource Map<String,Object> currentUser;
}
