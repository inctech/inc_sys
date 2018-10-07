package cn.inctech.app.common.exception;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RC_SYS_PROCESS_ERROR;

/**
 * App Sys Exception
 * @author ly
 */
@SuppressWarnings("serial")
public class AppSysException extends UDException {

	public AppSysException() {
		super();
	}

	public AppSysException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppSysException(String message) {
		super(message);
	}

	public AppSysException(Throwable cause) {
		super(cause);
	}
	
	public int getRetCode() {
		return RETCODE;
	}

	final static int RETCODE=RC_SYS_PROCESS_ERROR;
	
}
