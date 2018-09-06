package cn.inctech.app.biz.demo.user.dao;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class DemoUserModel implements Serializable {
	
	private String userid;
	@NotBlank(message = "用户名称不能为空")
	private String username;
	
	@NotBlank(message = "用户口令不能为空")
	private String password;
	
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;
	
	private boolean status;
	private String descr;
	
	@NotBlank(message = "手机不能为空")
	@Pattern(regexp="(^((13[0-9])|(15[^4,\\\\D])|(17[0-9])|(18[0-9]))\\\\d{8}$)?",message="手机格式错误")
	private String mobile;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday; 
}
