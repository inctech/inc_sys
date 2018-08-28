package cn.inctech.app.common.mvc;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_BOOL_FAILED;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_CODE_FAILED;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_CODE;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_MSG;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RESULT_KEY_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RETCODE_SYS_PROCESS_ERROR;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.inctech.app.common.exception.UDException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class SysActionHadnler {
	
	@ExceptionHandler(InsufficientAuthenticationException.class)//校验码错误的处理
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handle_iae_exception(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE, RESULT_CODE_FAILED);
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	@ExceptionHandler(UDException.class)//系统级别的异常
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handle_uc_exception(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE,((UDException)e).getRetCode());
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	@ExceptionHandler(Exception.class)//未预料的系统级别的异常
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Map<String, Object> handle_unknow_exception(HttpServletRequest request, Exception e) {
		log.debug(e.getMessage());
		Map<String, Object> m = new HashMap<>();
		m.put(RESULT_KEY_CODE,RETCODE_SYS_PROCESS_ERROR);
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, e.getMessage());
		return m;
	}
	
	@ExceptionHandler(value =BindException.class)
    @ResponseBody
    public Map<String, Object> handleBindException(BindException ex) throws BindException {
		Map<String, Object> m = new HashMap<>();
        StringBuilder err_msg = new StringBuilder();
        ex.getFieldErrors().stream().forEach(e->err_msg.append(e.getField()).append("=[").append(e.getRejectedValue()).append("]--").append(e.getDefaultMessage()).append("\n\r"));
        m.put(RESULT_KEY_CODE,RETCODE_SYS_PROCESS_ERROR);
		m.put(RESULT_KEY_SUCCESS, RESULT_BOOL_FAILED);
		m.put(RESULT_KEY_MSG, err_msg.toString());
        return m;
    }
}
