package cn.inctech.app.biz.demo.hello.service;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.inctech.app.biz.demo.user.dao.DemoUserMapper;
import cn.inctech.app.biz.demo.user.dao.DemoUserModel;

@Service
public class HelloServiceImp {

	public void insert_trans_test() {
		String err_uid="tom_001";
		Random r=new Random();
		String random_uid="jack"+r.nextInt(100);
		DemoUserModel u=new DemoUserModel();
		u.setDescr("Hello");
		u.setEmail("tom@inctech.cn");
		u.setPassword("tom123");
		u.setStatus(true);
		u.setUserid(random_uid);
		u.setUsername(random_uid);
		um.insert(u);
		
		u.setUserid(err_uid);
		random_uid="jack"+r.nextInt(100);
		u.setUsername(random_uid);
		um.insert(u);
	}
	
	@Resource DemoUserMapper um;
}
