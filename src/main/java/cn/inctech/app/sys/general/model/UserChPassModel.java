package cn.inctech.app.sys.general.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserChPassModel {

	@NotBlank(message = "旧口令不能为空")
	private String oldpass;//由前台完成密码两次输入是否一致
	
	@NotBlank(message = "新口令不能为空")
	private String newpass;//由前台完成密码两次输入是否一致
	
}
