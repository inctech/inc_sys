package cn.inctech.app.common.dao.jdbc.sql;

import lombok.Data;

@Data
public class TableModel {

	private String tablename;
	private String keyname;
	private boolean ignorekey=true;
	
}
