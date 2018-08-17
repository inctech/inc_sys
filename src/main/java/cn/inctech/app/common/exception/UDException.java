/**
 * Biz Logic Exception
 * @author ly
 */
package cn.inctech.app.common.exception;

@SuppressWarnings("serial")
public abstract class UDException extends RuntimeException {

	public UDException() {
		super();
	}

	public UDException(String message, Throwable cause) {
		super(message, cause);
	}

	public UDException(String message) {
		super(message);
	}

	public UDException(Throwable cause) {
		super(cause);
	}
	
	public abstract int getRetCode();

	final static int retCode=0;
}
