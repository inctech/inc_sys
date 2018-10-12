package cn.inctech.app.common.dao.jdbc.sql;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import cn.inctech.app.biz.demo.user.dao.DemoUserModel;
import cn.inctech.app.common.exception.AppSysException;

@Component
public class SqlGeneratorBean {

	public String updateSQL(Object entity, TableModel tab) {
		return updateSQL(entity,tab,MYBATIS);
	}

	public String insertSQL(Object entity, TableModel tab) {
		return insertSQL(entity,tab,MYBATIS);
	}
	public String insertSQL(Object entity, TableModel tab, String type) {
		String sql = null;
		switch (type) {
		case MYBATIS:
			sql = insertMybatisSql(entity, tab);
			break;
		case JDBC:
			sql = insertJdbcSql(entity, tab);
			break;
		}
		return sql;
	}
	
	public String updateSQL(Object entity, TableModel tab, String type) {
		String sql = null;
		switch (type) {
		case MYBATIS:
			sql = updateMybatisSql(entity, tab);
			break;
		case JDBC:
			sql = updateJdbcSql(entity, tab);
			break;
		}
		return sql;
	}

	String insertJdbcSql(Object entity, TableModel tab) {
		String r=insertMybatisSql(entity,tab);
		r=r.replaceAll("#\\{(\\w+)\\}", ":$1");
		return r;
	}

	@SuppressWarnings("unchecked")
	String insertMybatisSql(Object entity, TableModel tab) {
		String r=null;
		if(entity instanceof Map)
			r=buildInsertSqlFromMap((Map<String,Object>)entity,tab);
		else
			r=buildInsertSqlFromBean(entity,tab);
		return r;
	}
	
	String updateJdbcSql(Object entity, TableModel tab) {
		String r=updateMybatisSql(entity,tab);
		r=r.replaceAll("#\\{(\\w+)\\}", ":$1");
		return r;
	}

	@SuppressWarnings("unchecked")
	String updateMybatisSql(Object entity, TableModel tab) {
		String r=null;
		if(entity instanceof Map)
			r=buildUpdateSqlFromMap((Map<String,Object>)entity,tab);
		else
			r=buildUpdateSqlFromBean(entity,tab);
		return r;
	}
	
	String buildInsertSqlFromMap(Map<String,Object> entity,TableModel tab) {
		StringBuilder sql = new StringBuilder();
		StringBuilder sql1 = new StringBuilder();
		StringBuilder sql2 = new StringBuilder();
		sql1.append("insert into ").append(tab.getTablename());
		sql1.append("(");
		sql2.append("(");
		for(String k:entity.keySet()) {
			if("class".equalsIgnoreCase(k))
				continue;
			if(entity.get(k)!=null) {
				if(tab.isIgnorekey()&&tab.getKeyname().equalsIgnoreCase(k))
					continue;
				sql1.append(k).append(",");
				sql2.append("#{").append(k).append("}").append(",");
			}
		}
		sql1.deleteCharAt(sql1.length()-1);
		sql1.append(")");
		sql2.deleteCharAt(sql2.length()-1);
		sql2.append(")");
		sql=sql1.append(" values ").append(sql2);
		return sql.toString();
	}
	
	
	String buildInsertSqlFromBean(Object entity,TableModel tab) {
		Map<String,Object> p=bean2map(entity);
		return buildInsertSqlFromMap(p,tab);
	}
	
	Map<String,Object> bean2map(Object bean){
		Map<String,String> m=null;
		try {
			m=BeanUtils.describe(bean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppSysException("bean to map error!");
		}
		Map<String,Object> p=new HashMap<String,Object>();
		Object v=null;
		for(String k:m.keySet()) {
			if("class".equalsIgnoreCase(k))
				continue;
			if((v=m.get(k))!=null)
				p.put(k, v);
		}
		return p;
	}
	
	String buildUpdateSqlFromMap(Map<String,Object> entity,TableModel tab) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tab.getTablename()).append(" set ");
		for(String k:entity.keySet()) {
			if(entity.get(k)!=null) {
				if(tab.isIgnorekey()&&tab.getKeyname().equalsIgnoreCase(k))
					continue;
				sql.append(" ").append(k).append("=").append("#{").append(k).append("},");
			}
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(" where ").append(tab.getKeyname()).append("=#{").append(tab.getKeyname()).append("}");
		return sql.toString();
	}
	
	String buildUpdateSqlFromBean(Object entity,TableModel tab) {
		Map<String,Object> p=bean2map(entity);
		return buildUpdateSqlFromMap(p,tab);
	}
	
	static final String MYBATIS="mybatis";
	static final String JDBC="jdbc";
	
	public static void main(String[] args) {
		SqlGeneratorBean sg=new SqlGeneratorBean();
		TableModel tab=new TableModel();
		tab.setKeyname("id");
		tab.setTablename("user");
		Map<String,Object> rec=new HashMap<String,Object>();
		rec.put("id", Integer.valueOf(100));
		rec.put("username", "tom");
		rec.put("password", "pass");
		rec.put("email", "tom@163.com");
		rec.put("descr", null);
		System.out.println(sg.insertSQL(rec, tab));
		System.out.println(sg.updateSQL(rec, tab));
		
		tab.setKeyname("userid");
		tab.setIgnorekey(false);
		DemoUserModel dm=new DemoUserModel();
		dm.setBirthday(new Date(System.currentTimeMillis()));
		dm.setEmail("xx@xx.com");
		dm.setMobile("13111111111");
		dm.setUserid("admin");
		System.out.println(sg.insertSQL(dm, tab, "jdbc"));
		System.out.println(sg.updateSQL(rec, tab,"jdbc"));
	}
}
