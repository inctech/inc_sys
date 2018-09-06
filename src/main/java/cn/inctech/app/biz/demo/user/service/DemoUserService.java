package cn.inctech.app.biz.demo.user.service;

import java.util.List;

import cn.inctech.app.biz.demo.user.dao.DemoUserModel;

public interface DemoUserService {

	public List<DemoUserModel> queryUsers();
	public DemoUserModel queryUser();
	public void insert(DemoUserModel u);
	public void update(DemoUserModel u);
	
}
