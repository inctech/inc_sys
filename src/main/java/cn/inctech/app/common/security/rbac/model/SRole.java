package cn.inctech.app.common.security.rbac.model;

import java.util.List;

import lombok.Data;

@Data
public class SRole {
	private String roleid;
	private String rolename;
	private List<SRes> ress;
}
