package cn.inctech.app.biz.talents.jobseeker.action;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RC_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RK_CODE;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;
import cn.inctech.app.biz.talents.jobseeker.service.JobSeekerService;

@RestController
@RequestMapping("/talents")
public class JobseekerAction {

	@RequestMapping("/info/save")
	@ResponseBody
	public Map<String,Object> finishResume(@RequestBody @Valid ResumeModel m){
		Map<String,Object> r=new HashMap<String, Object>();
		jss.savetResume(m);
		r.put(RK_CODE, RC_SUCCESS);
		return r;
	}
	
	@Resource JobSeekerService jss;
	
}
