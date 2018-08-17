package cn.inctech.app.biz.demo.user.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.inctech.app.biz.demo.user.dao.UserModel;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpTest {

	@Test
	public void testQueryUsers() {
	}

	@Test
	public void testQueryUser() {
	}

	@Test
	public void testInsert() {
		UserModel u=new UserModel();
		u.setDescr("Hello");
		u.setEmail("tom@inctech.cn");
		u.setPassword("tom123");
		u.setStatus(true);
		u.setUserid("tom_001");
		u.setUsername("tom_sir");
		us.insert(u);
	}

	@Test
	public void testUpdate() {
	}
	
	@Resource UserService us;

}
