package cn.inctech.app.common.advice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import cn.inctech.app.common.exception.AppSysException;
import cn.inctech.app.common.exception.BizLogicException;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class BizAdvice {
	
	@Around("pointcut_dao() || pointcut_service()")
	public Object dao_handle(ProceedingJoinPoint pjp) throws Throwable {
		Object r=null;
		String method="";
		try {
			method=pjp.getSignature().getDeclaringTypeName()+" : "+pjp.getSignature().getName();
			log.info("mehtod--["+method+"] start at "+sdf.format(new Date())+"...");
			r=pjp.proceed();
		}catch(DataAccessException e) {
			log.info("mehtod--["+method+"] error: "+e.getMessage());
			throw new BizLogicException(e);
		}catch(Exception e) {
			log.info("mehtod--["+method+"] error: "+e.getMessage());
			throw new AppSysException(e);
		}finally{
			log.info("mehtod--["+method+"] end at "+sdf.format(new Date())+"...");
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
}
