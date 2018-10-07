package cn.inctech.app.sys.general.action;

import static cn.inctech.app.common.cfg.param.GlobalConfig.*;

import static cn.inctech.app.sys.param.SysParam.CU_KEY_USERNAME;
import static cn.inctech.app.sys.param.SysParam.CU_KEY_USERROLE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.sys.bean.SysServiceBean;
import cn.inctech.app.sys.general.model.UserModel;
import cn.inctech.app.sys.general.service.GeneralService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GeneralAction {

	@RequestMapping(UA_USER_REGIST)
	@ResponseBody
	public Map<String,Object> regist_user(@Valid @RequestBody UserModel u,HttpServletRequest request){
		String ui_smscode=u.getSmscode();
		String ui_imgcode=u.getImgcode();
		String sys_smscode=""+request.getSession().getAttribute(SMS_CODE_KEY_OF_CUR_SESSION);
		String sys_imgcode=request.getSession().getAttribute(skey)+"";
		if(!sys_smscode.equals(ui_smscode)||!sys_imgcode.equals(ui_imgcode)) {
			log.info(ui_smscode+":"+sys_smscode+","+ui_imgcode+":"+sys_imgcode);
			throw new AppSysException(SMS_CODE_CHECK_ERROR);
		}
		log.info("user regist:"+u);
		gs.registUser(u);
		request.getSession().removeAttribute(SMS_CODE_KEY_OF_CUR_SESSION);
		request.getSession().removeAttribute(skey);
		Map<String,Object> r=new HashMap<>();
		r.put("success", Boolean.TRUE);
		return r;
	}
	
	@RequestMapping("/sys/userinfo")
	@ResponseBody
	public Map<String,Object> userinfo(){
		Map<String,Object> r=new HashMap<>();
		r.put("success", Boolean.TRUE);
		r.put("username", currentUser.get(CU_KEY_USERNAME));
		r.put("roles", currentUser.get(CU_KEY_USERROLE));
		return r;
	}
	
	@RequestMapping("/sys/logout")
	@ResponseBody
	public Map<String,Object> sys_logout(HttpServletRequest request){
		request.getSession().invalidate();
		Map<String,Object> r=new HashMap<>();
		r.put("success", Boolean.TRUE);
		return r;
	}
	
	@RequestMapping(U_SYS_CODE+"/all")
	@ResponseBody
	public Map<String,Object> allcode(){
		Map<String,Object> r=new HashMap<>();
		r.put("success", Boolean.TRUE);
		r.put("data", ss.getAllCode());
		return r;
	}
	
	@RequestMapping(U_SYS_CODE+"/{property}")
	@ResponseBody
	public Map<String,Object> getcode(@PathVariable("property") String property){
		Map<String,Object> r=new HashMap<>();
		r.put("success", Boolean.TRUE);
		r.put("data", ss.getCode(property));
		return r;
	}
	
	@RequestMapping(value = U_SMS_CODE+"/{mobile}")
	@ResponseBody
    public Map<String,Object> get_sms_code(@PathVariable("mobile") String mobile,HttpServletRequest request) {
		String code=ss.sendSmsCode(mobile);
		log.info("["+mobile+"]--"+code);
		request.getSession().setAttribute(SMS_CODE_KEY_OF_CUR_SESSION, code);
		Map<String,Object> r=new HashMap<>();
		r.put(RK_CODE, RC_SUCCESS);
		r.put(RK_SUCCESS, RV_B_SUCCESS);
		r.put(RK_MSG, RM_SUCCESS_LOGIN);
		return r;
	}
	
	final static String SMS_CODE_CHECK_ERROR="短信验证码或者图形验证码不匹配，请检查您的输入";
	@Value("${kaptcha.session.key}") String skey;
	@Resource GeneralService gs;
	@Resource SysServiceBean ss;
	@Resource Map<String,Object> currentUser;
}
