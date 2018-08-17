package cn.inctech.app.biz.demo.user.dao;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
@SuppressWarnings("serial")
public class UserModel implements Serializable {

	private String userid;
	private String username;
	private String password;
	private String email;
	private boolean status;
	private String descr;
}
