package cn.inctech.app.biz.demo.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.inctech.app.biz.demo.user.dao.UserModel;
import cn.inctech.app.biz.demo.user.service.UserServiceImp;

@RestController
public class UserAction {

	@RequestMapping("/user_insert")
	public Map<String,Object> user_insert(@Valid UserModel m){
		Map<String,Object> r=new HashMap<>();
		service.insert(m);
		return r;
	}
	
	@RequestMapping("/user_query")
	public Map<String,Object> user_query(UserModel m){
		Map<String,Object> r=new HashMap<>();
		r.put("data", service.queryUsers());
		return r;
	}
	
	@Resource UserServiceImp service;
}
