package cn.inctech.app.common.security.rbac.model;

import java.util.List;

import lombok.Data;

@Data
public class SRes {
	private String resid;
	private String resname;
	private String resurl;
	private List<SRole> roles;
}
