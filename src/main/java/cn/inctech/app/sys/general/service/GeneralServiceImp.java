package cn.inctech.app.sys.general.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.inctech.app.common.exception.BizLogicException;
import cn.inctech.app.common.util.GeneralUtil;
import cn.inctech.app.sys.general.dao.GeneralMapper;
import cn.inctech.app.sys.general.model.UserModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GeneralServiceImp implements GeneralService {

	@Override
	public void registUser(UserModel u) {
		if(gm.queryBUserByUseridOrMobile(u)>0) {
			log.error(USERID_MOBILE_ERROR);
			throw new BizLogicException(USERID_MOBILE_ERROR);
		}
		u.setPassword(gu.buildBcrPass(u.getPassword()));
		gm.registUser(u);
	}
	
	@Override
	public void updatePassByUserid(String userid,String oldPass,String newPass) {
		UserModel u=gm.queryUserById(userid);
		String c=u.getPassword();
		if(!gu.isMatchBcrPass(oldPass, c))
			throw new BizLogicException(PASS_ERROR);
		u=new UserModel();
		u.setUserid(userid);
		newPass=gu.buildBcrPass(newPass);
		u.setPassword(newPass);
		gm.updateUser(u);
	}

	/**
	 * 修改b_user,t_resume表中的mobile
	 */
	@Override
	public void updateMobileByUserid(String userid, String mobile) {
		UserModel u=new UserModel();
		u.setUserid(userid);
		u.setMobile(mobile);
		gm.updateUser(u);
		gm.updateMobileByUserid(userid, mobile);
	}
	
	static final String USERID_MOBILE_ERROR="用户id或者手机号不唯一";
	static final String PASS_ERROR="旧口令输入有误";
	
	@Resource GeneralMapper gm;
	@Resource GeneralUtil gu;
	
	public static void main(String[] args) {
	}

}
