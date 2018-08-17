package cn.inctech.app.common.advice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.common.exception.BizLogicException;

@Aspect
@Component
public class BizAdvice {
	
	@Around("pointcut_dao()")
	public Object dao_handle(ProceedingJoinPoint pjp) throws Throwable {
		Object r=null;
		String method="";
		try {
			method=pjp.getSignature().getName();
			log.info("dao mehtod--["+method+"] start at "+sdf.format(new Date())+"...");
			r=pjp.proceed();
		}catch(DataAccessException e) {
			log.info("dao mehtod--["+method+"] error: "+e.getMessage());
			throw new BizLogicException(e);
		}catch(Exception e) {
			log.info("dao mehtod--["+method+"] error: "+e.getMessage());
			throw new AppSysException(e);
		}finally{
			log.info("dao mehtod--["+method+"] end at "+sdf.format(new Date())+"...");
		}
		return r;
	}
	
	@Around("pointcut_service()")
	public Object service_handle(ProceedingJoinPoint pjp) throws Throwable {
		Object r=null;
		try {
			String method=pjp.getSignature().getName();
			log.debug("service mehtod--["+method+"] start at "+sdf.format(new Date())+"...");
			r=pjp.proceed();
			log.debug("service mehtod--["+method+"] end at "+sdf.format(new Date())+"...");
		}catch(BizLogicException e) {
			throw e;
		}catch(AppSysException e) {
			throw e;
		}finally{
			
		}
		return r;
	}
	
	@Pointcut(POINTCUT_SERVICE)  
    public void pointcut_service(){}
	
	@Pointcut(POINTCUT_DAO)  
    public void pointcut_dao(){}
	
	@Value("${webapp.pc.service}") String pc_service;
	/*static final String POINTCUT_SERVICE="execution(* ${webapp.pc.service}.*(..))";
	static final String POINTCUT_DAO="execution(* ${webapp.pc.dao}.*(..))";*/
	
	static final String POINTCUT_SERVICE="execution(* cn.inctech.app..*ServiceImp*.*(..))";
	static final String POINTCUT_DAO="execution(* cn.inctech.app..*Mapper*.*(..))";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	Logger log = LoggerFactory.getLogger(this.getClass());
}
