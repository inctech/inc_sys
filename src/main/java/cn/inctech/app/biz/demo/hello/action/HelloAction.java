package cn.inctech.app.biz.demo.hello.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloAction {
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
