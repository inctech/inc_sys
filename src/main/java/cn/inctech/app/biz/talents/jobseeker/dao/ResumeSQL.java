package cn.inctech.app.biz.talents.jobseeker.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;
import cn.inctech.app.common.dao.jdbc.sql.SqlGeneratorBean;
import cn.inctech.app.common.dao.jdbc.sql.TableModel;

@Component
public class ResumeSQL {

	public String insertSQL(ResumeModel params) {
		return ssg.insertSQL(params, getTabConfig());
	}
	
	public String updateSQL(ResumeModel params) {
		return ssg.updateSQL(params, getTabConfig());
	}
	
	TableModel getTabConfig() {
		TableModel tab=new TableModel();
		tab.setIgnorekey(false);
		tab.setKeyname("resid");
		tab.setTablename("t_resume");
		return tab;
	}
	
	@Resource SqlGeneratorBean ssg;
}
