package cn.inctech.app.sys.param;

public class SysParam {

	public static final String POINTCUT_SERVICE="execution(* cn.inctech.app..*ServiceImp*.*(..))";
	public static final String POINTCUT_DAO="execution(* cn.inctech.app..*Mapper*.*(..)) || execution(* cn.inctech.app..*Dao*.*(..))";
}
