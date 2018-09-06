package cn.inctech.app.biz.demo.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.inctech.app.biz.demo.user.dao.DemoUserMapper;
import cn.inctech.app.biz.demo.user.dao.DemoUserModel;
import cn.inctech.app.common.util.GeneralUtil;

@Service
public class DemoUserServiceImp implements DemoUserService {

	public List<DemoUserModel> queryUsers(){
		return um.queryUsers();
	}
	
	public DemoUserModel queryUser() {
		return um.queryUser();
	}
	
	public void insert(DemoUserModel u) {
		u.setPassword(gutil.buildBcrPass(u.getPassword()));
		um.insert(u);
	}
	
	public void update(DemoUserModel u) {
		um.update(u);
	}
	
	@Resource DemoUserMapper um;
	@Resource GeneralUtil gutil;
}
