package cn.inctech.app.biz.demo.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.inctech.app.biz.demo.user.dao.UserMapper;
import cn.inctech.app.biz.demo.user.dao.UserModel;
import cn.inctech.app.common.util.GeneralUtil;

@Service
public class UserServiceImp implements UserService {

	public List<UserModel> queryUsers(){
		return um.queryUsers();
	}
	
	public UserModel queryUser() {
		return um.queryUser();
	}
	
	public void insert(UserModel u) {
		u.setPassword(gutil.buildBcrPass(u.getPassword()));
		um.insert(u);
	}
	
	public void update(UserModel u) {
		um.update(u);
	}
	
	@Resource UserMapper um;
	@Resource GeneralUtil gutil;
}
