package cn.inctech.app.sys.param;

public class SysParam {

	public static final String SQL_QUERY_USER_BY_NAME="select userid,password,‘1’ from select * from s_user where userid =?";
    public static final String SQL_QUERY_AUTH_BY_NAME="select u.userid username,r.rolename as authority from s_user u join s_user_role ur on u.userid=ur.userid join s_role r on r.roleid=ur.roleid where u.userid=?";
    
	public static final String POINTCUT_SERVICE="execution(* cn.inctech.app..*ServiceImp*.*(..))";
	public static final String POINTCUT_DAO="execution(* cn.inctech.app..*Mapper*.*(..)) || execution(* cn.inctech.app..*Dao*.*(..))";
	
	
	//CR=>current_request	request
	//CU=>current_user		session
	
	public static final String CR_CURRENT_PAGE="currentPage";
	public static final String CR_PAGE_SIEZ="pageSize";
	
	public static final String CU_KEY_USERNAME="cu_username";
	public static final String CU_KEY_USERROLE="cu_userrole";
}
