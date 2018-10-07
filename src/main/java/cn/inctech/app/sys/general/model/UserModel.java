package cn.inctech.app.sys.general.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserModel {

	@NotBlank(message = "用户名称不能为空")
	private String userid;//用户id，自己填写，后台唯一性验证
	
	@NotBlank(message = "手机不能为空")
	@Pattern(regexp="^[1][3,4,5,7,8][0-9]{9}$",message="手机格式错误")
	private String mobile;//用户手机，自己填写，使用验证码验证和后台唯一性验证
	
	private String username;//使用userid
	
	@NotBlank(message = "用户口令不能为空")
	private String password;//由前台完成密码两次输入是否一致
	
	private boolean status;//注册时自动设置为真
	
	//@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;
	
	private String descr;
	
	//只在注册时使用
	private String smscode;
	private String imgcode;
}
