package cn.inctech.app.sys.action;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_BOOL_FAILED;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_BOOL_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_CODE_FAILED;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_CODE_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_CODE;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_MSG;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_MSG_PASS_ERROR;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_MSG_SUCCESS_LOGIN;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_MSG_VALID_CODE_ERR;
import static cn.inctech.app.common.cfg.param.GlobalConfig.SMS_CODE_KEY_OF_CUR_SESSION;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_PAGE;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_PASS_ERROR;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_SMS_CODE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.sys.bean.SysServiceBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		throw new AppSysException("资源访问被拒绝，请确认访问权限");
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
	
	@RequestMapping(value = URL_SMS_CODE+"/{mobile}")
	@ResponseBody
    public Map<String,Object> get_sms_code(@PathVariable("mobile") String mobile,HttpServletRequest request) {
		String code=ss.sendSmsCode(mobile);
		log.info("["+mobile+"]--"+code);
		request.getSession().setAttribute(SMS_CODE_KEY_OF_CUR_SESSION, code);
		Map<String,Object> r=new HashMap<>();
		r.put(RESULT_KEY_CODE, RESULT_CODE_SUCCESS);
		r.put(RESULT_KEY_SUCCESS, RESULT_BOOL_SUCCESS);
		r.put(RESULT_KEY_MSG, RESULT_MSG_SUCCESS_LOGIN);
		return r;
	}
	
	@Resource SysServiceBean ss;
	
}
