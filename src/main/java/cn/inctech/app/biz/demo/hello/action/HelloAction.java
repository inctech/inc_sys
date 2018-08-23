package cn.inctech.app.biz.demo.hello.action;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@Secured({"admin","teacher"})
	@ResponseBody
	@RequestMapping("/talents/teacher/test01")
	public String teacher_test01() {
		log.info(""+currentUser);
		return "index";
	}
	
	@Resource Map<String,Object> currentUser;
}
