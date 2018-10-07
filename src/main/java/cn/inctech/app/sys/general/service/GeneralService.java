package cn.inctech.app.sys.general.service;

import cn.inctech.app.sys.general.model.UserModel;

public interface GeneralService {

	/**
	 * 求职用户自行注册
	 * @param u
	 */
	void registUser(UserModel u);
	
	/**
	 * 修改用户口令，需要输入原口令，原口令错误不修改为新口令
	 * @param userid
	 * @param oldPass
	 * @param newPass
	 */
	void updatePassByUserid(String userid,String oldPass,String newPass);
	
	/**
	 * 修改手机号
	 * @param userid
	 * @param mobile
	 */
	void updateMobileByUserid(String userid,String mobile);
}
