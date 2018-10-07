package cn.inctech.app.sys.general.sql;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.inctech.app.common.dao.jdbc.sql.SqlGeneratorBean;
import cn.inctech.app.common.dao.jdbc.sql.TableModel;
import cn.inctech.app.sys.general.model.UserModel;

@Component
public class UserSQL {

	public String insertSQL(UserModel params) {
		return ssg.insertSQL(params, getTabConfig());
	}
	
	public String updateSQL(UserModel params) {
		return ssg.updateSQL(params, getTabConfig());
	}
	
	TableModel getTabConfig() {
		TableModel tab=new TableModel();
		tab.setIgnorekey(false);
		tab.setKeyname("userid");
		tab.setTablename("b_user");
		return tab;
	}
	
	@Resource SqlGeneratorBean ssg;
}
