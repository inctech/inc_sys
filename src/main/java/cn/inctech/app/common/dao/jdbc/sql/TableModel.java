package cn.inctech.app.common.dao.jdbc.sql;

import lombok.Data;

@Data
public class TableModel {

	private String tablename;
	private String keyname;//可以单主键，可多主键,[,#-]分离
	private boolean ignorekey=true;
	
}
