package cn.inctech.app.sys.action;

import static cn.inctech.app.common.cfg.param.GlobalConfig.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.inctech.app.common.exception.AppSysException;

@Controller
public class SysAction {

	@RequestMapping(value = URL_LOGIN, method = RequestMethod.GET)
	@ResponseBody
    public void login_error(@RequestParam(value = "error", required = false) String error) {
		throw new InsufficientAuthenticationException(RESULT_MSG_VALID_CODE_ERR);
	}
	
	@RequestMapping(value = URL_LOGIN, method = RequestMethod.POST)
	@ResponseBody
    public void pass_error() {
		throw new InsufficientAuthenticationException(RESULT_MSG_PASS_ERROR);
	}
	
	@RequestMapping("/403")
	@ResponseBody
    public void access_403() {
		throw new AppSysException("resource access denied,please try to access again");
	}
	
	@RequestMapping(value = URL_LOGIN_PAGE)
    public String login_page() {
		return "login";
	}
	
	@RequestMapping(value = URL_LOGIN_SUCCESS)
	@ResponseBody
    public Map<String,Object> login_success() {
		Map<String,Object> r=new HashMap<>();
		r.put(RESULT_KEY_CODE, RESULT_CODE_SUCCESS);
		r.put(RESULT_KEY_SUCCESS, RESULT_BOOL_SUCCESS);
		r.put(RESULT_KEY_MSG, RESULT_MSG_SUCCESS_LOGIN);
		return r;
	}
	
	@RequestMapping(value = URL_LOGIN_PASS_ERROR)
	@ResponseBody
    public Map<String,Object> login_pass_error() {
		Map<String,Object> r=new HashMap<>();
		r.put(RESULT_KEY_CODE, RESULT_CODE_FAILED);
		r.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		r.put(RESULT_KEY_MSG, RESULT_MSG_PASS_ERROR);
		return r;
	}
	
}
