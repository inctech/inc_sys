package cn.inctech.app.common.advice;

import static cn.inctech.app.sys.param.SysParam.POINTCUT_DAO;
import static cn.inctech.app.sys.param.SysParam.POINTCUT_SERVICE;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
	public Object biz_handle(ProceedingJoinPoint pjp) throws Throwable {
		Object r=null;
		String method="";
		try {
			method=pjp.getSignature().getDeclaringTypeName()+" : "+pjp.getSignature().getName();
			log.info("mehtod--["+method+"] start at "+sdf.format(new Date())+"...");
			r=pjp.proceed();
		}catch(DataAccessException e) {
			log.error("mehtod--["+method+"] error: "+e.getMessage());
			throw new BizLogicException(e);
		}catch(BizLogicException e) {
			throw e;
		}catch(AppSysException e) {
			throw e;
		}
		catch(Exception e) {
			log.error("mehtod--["+method+"] error: "+e.getMessage());
			e.printStackTrace();
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
	
	static final String L_POINTCUT_SERVICE="execution(* cn.inctech.app..*ServiceImp*.*(..))";
	static final String L_POINTCUT_DAO="execution(* cn.inctech.app..*Mapper*.*(..)) || execution(* cn.inctech.app..*Dao*.*(..))";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
