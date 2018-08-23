package cn.inctech.app.common.cfg.param;

public class GlobalConfig {

	public static final String RESULT_MSG_VALID_CODE_ERR="verify code entered is wrong";
	public static final String RESULT_MSG_VALID_CODE_BLANK="verify code is blank";
	public static final String RESULT_MSG_SUCCESS_LOGIN="login success";
	public static final String RESULT_MSG_PASS_ERROR="username or password is invalid";
	
	public static final String RESULT_KEY_CODE="code";
	public static final String RESULT_KEY_MSG="message";
	public static final String RESULT_KEY_SUCCESS="success";
	
	public static final String RESULT_MSG_SUCCESS="success";
	public static final Boolean RESULT_BOOL_SUCCESS=Boolean.TRUE;
	public static final Boolean RESULT_BOOL_FAILED=Boolean.FALSE;
	public static final String RESULT_MSG_FAILED="failed";
	
	public static final int RESULT_CODE_SUCCESS=1;
	public static final int RESULT_CODE_FAILED=-1;
	
	
	
	/***********************************URL**************************************************************/
	public static final String URL_LOGIN_ERROR="/login?error";
	public static final String URL_IMGCODE_ERROR="/imgcode_error";
	public static final String URL_LOGIN_SUCCESS="/login_success";
	public static final String URL_LOGIN_PASS_ERROR="/login_pass_error";
	public static final String URL_LOGIN_PAGE="/login_page";
	public static final String URL_LOGIN="/login";
	public static final String URL_LOGOUT="/logout";
	public static final String URL_KAPTCHA="/sys/code";
	
	public static final String URL_ALLOW_CSS="/css/**";
	public static final String URL_ALLOW_JS="/js/**";
	public static final String URL_ALLOW_FONTS="/fonts/**";
	public static final String URL_ALLOW_INDEX="/index*";
	
	/***********************************RETURN CODE*******************************************************/
	public static final int RETCODE_NOT_INNER_SERVICE=440;//内部服务不存在
	public static final int RETCODE_HTTP_PROCESS_ERROR=480;//Http Error
	
	public static final int RETCODE_PROCESS_RIGHT=0;//正确返回
	public static final int RETCODE_USER_NOT_LOGIN=400;//未登录
	public static final int RETCODE_NOT_AUTH=401;//未授权
	public static final int RETCODE_ACCESS_DENIED=403;//资源访问被拒绝
	public static final int RETCODE_NOT_SERVICE=404;//无此服务
	public static final int RETCODE_SAME_OBJECT_NAME=405;//
	
	public static final int RETCODE_BS_PROCESS_ERROR=-100;//业务处理错误返回
	public static final int RETCODE_SYS_PROCESS_ERROR=-200;//系统处理错误返回
	public static final int RETCODE_PARAM_INVALID=-201;//参数无效
	public static final int RETCODE_NOT_ACCESSKEY=-202;//超时
	public static final int RETCODE_CONSUMER_SIGN_ERROR=-205;//签名sign验证无效
	public static final int RETCODE_TIME_OUT=-206;//超时
	public static final int RETCODE_IP_ERROR=-208;//IP验证错误
	
}
