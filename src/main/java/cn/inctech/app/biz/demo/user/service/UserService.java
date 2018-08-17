package cn.inctech.app.biz.demo.user.service;

import java.util.List;

import cn.inctech.app.biz.demo.user.dao.UserModel;

public interface UserService {

	public List<UserModel> queryUsers();
	public UserModel queryUser();
	public void insert(UserModel u);
	public void update(UserModel u);
	
}
