package cn.inctech.app.common.advice;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RC_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RK_CODE;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RK_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RV_B_SUCCESS;
import static cn.inctech.app.sys.param.SysParam.POINTCUT_ACTION;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
//after @ControllerAdvice
public class CtrlAdvice {
	
	@AfterReturning(pointcut = "pointcut_action()", returning = "r")
	public void actionPageAfterHandle(Map<String, Object> r) {
		if (null == r) {
			log.debug("action result is null! return new a HashMap!");
			r = new HashMap<String, Object>();
		}
		if(!r.containsKey(RK_CODE))
			r.put(RK_CODE, RC_SUCCESS);
		if(!r.containsKey(RK_SUCCESS))
			r.put(RK_SUCCESS, RV_B_SUCCESS);
	}
	
    @Pointcut(POINTCUT_ACTION)  
    public void pointcut_action(){}
	
}
