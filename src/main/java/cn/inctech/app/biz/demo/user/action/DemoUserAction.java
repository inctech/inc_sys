package cn.inctech.app.biz.demo.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.inctech.app.biz.demo.user.dao.DemoUserModel;
import cn.inctech.app.biz.demo.user.service.DemoUserServiceImp;

@RestController
public class DemoUserAction {

	@RequestMapping("/user_insert")
	public Map<String,Object> user_insert(@Valid DemoUserModel m){
		Map<String,Object> r=new HashMap<>();
		service.insert(m);
		return r;
	}
	
	@RequestMapping("/user_query")
	public Map<String,Object> user_query(DemoUserModel m){
		Map<String,Object> r=new HashMap<>();
		r.put("data", service.queryUsers());
		return r;
	}
	
	@Resource DemoUserServiceImp service;
}
