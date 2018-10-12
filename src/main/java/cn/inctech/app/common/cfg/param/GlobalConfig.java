package cn.inctech.app.common.cfg.param;

/**
 * 
 * @author ly
 * [UA] prefix means this url is allowed unrestricted access
 * [RM] prefix means result message
 * [RC] prefix means result code
 * [RK] prefix means result key
 * [RV] prefix means result value B means boolean ,S means String
 * [U] prefix means url
 */
public class GlobalConfig {

	public static final String RM_VALID_CODE_ERR="verify code entered is wrong";
	public static final String RM_VALID_CODE_BLANK="verify code is blank";
	public static final String RM_SUCCESS_LOGIN="login success";
	public static final String RM_PASS_ERROR="username or password is invalid";
	
	public static final String RK_CODE="code";
	public static final String RK_MSG="message";
	public static final String RK_SUCCESS="success";
	public static final String RK_DATA="data";
	
	public static final String RV_S_SUCCESS="success";
	public static final Boolean RV_B_SUCCESS=Boolean.TRUE;
	public static final Boolean RV_B_FAILED=Boolean.FALSE;
	public static final String RV_S_FAILED="failed";
	
	public static final int RC_SUCCESS=1;
	public static final int RC_FAILED=-1;
	
	/***********************************URL**************************************************************/
	public static final String U_LOGIN_ERROR="/login?error";
	public static final String U_IMGCODE_ERROR="/imgcode_error";
	public static final String U_LOGIN_SUCCESS="/login_success";
	public static final String U_LOGIN_PASS_ERROR="/login_pass_error";
	public static final String U_LOGIN_PAGE="/login_page";
	public static final String U_LOGIN="/login";
	public static final String U_LOGOUT="/logout";
	public static final String U_IMG_CODE="/sys/img_code";
	public static final String U_SMS_CODE="/sys/sms_code";
	public static final String U_SYS_CODE="/sys/sys_code";
	
	public static final String URL_INDEX="/index*";
	
	
	public static final String UA_USER_REGIST="/sys/user_regist";
	public static final String UA_CSS="/css/**";
	public static final String UA_JS="/js/**";
	public static final String UA_FONTS="/fonts/**";
	public static final String UA_FAVICON="/favicon.ico";
	public static final String UA_TEST="/sys/test/**";
	
	/***********************************RETURN CODE*******************************************************/
	public static final int RC_NOT_INNER_SERVICE=440;//内部服务不存在
	public static final int RC_HTTP_PROCESS_ERROR=480;//Http Error
	
	public static final int RC_PROCESS_RIGHT=0;//正确返回
	public static final int RC_USER_NOT_LOGIN=400;//未登录
	public static final int RC_NOT_AUTH=401;//未授权
	public static final int RC_ACCESS_DENIED=403;//资源访问被拒绝
	public static final int RC_NOT_SERVICE=404;//无此服务
	public static final int RC_SAME_OBJECT_NAME=405;//
	
	public static final int RC_BS_PROCESS_ERROR=-100;//业务处理错误返回
	public static final int RC_SYS_PROCESS_ERROR=-200;//系统处理错误返回
	public static final int RC_PARAM_INVALID=-201;//参数无效
	public static final int RC_NOT_ACCESSKEY=-202;//超时
	public static final int RC_CONSUMER_SIGN_ERROR=-205;//签名sign验证无效
	public static final int RC_TIME_OUT=-206;//超时
	public static final int RC_IP_ERROR=-208;//IP验证错误
	
	public static final String SMS_CODE_KEY_OF_CUR_SESSION="inctech_sms_code_key_of_cur_session";

}
