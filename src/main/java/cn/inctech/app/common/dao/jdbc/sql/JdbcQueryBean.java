package cn.inctech.app.common.dao.jdbc.sql;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcQueryBean {

	public Map<String,Object> querySingle(String sql,Map<String,?> param) {
		return npjt.queryForMap(sql, param);
	}
	
	public List<Map<String,Object>> queryMulti(String sql,Map<String,?> param) {
		return npjt.queryForList(sql, param);
	}
	
	public Map<String,Object> querySingle(String sql,Object[] param) {
		return jt.queryForMap(sql, param);
	}
	
	public List<Map<String,Object>> queryMulti(String sql,Object[] param) {
		return jt.queryForList(sql, param);
	}
	
	@Resource NamedParameterJdbcTemplate npjt;
	@Resource JdbcTemplate jt;
}
