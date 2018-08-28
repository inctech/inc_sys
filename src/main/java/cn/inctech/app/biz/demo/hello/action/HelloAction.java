package cn.inctech.app.biz.demo.hello.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.inctech.app.biz.demo.user.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HelloAction {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/home")
	public String home() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("/talents/teacher/test01")
	public Map<String,Object> teacher_test01() {
		Map<String,Object> r=new HashMap<>();
		log.info(""+currentUser);
		r.put("data", um.queryUsers());
		return r;
	}
	
	@Resource UserMapper um;
	@Resource Map<String,Object> currentUser;
}
