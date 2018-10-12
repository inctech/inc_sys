package cn.inctech.app.biz.talents.jobseeker.action;

import static cn.inctech.app.common.cfg.param.GlobalConfig.RK_DATA;
import static cn.inctech.app.common.cfg.param.GlobalConfig.RK_MSG;
import static cn.inctech.app.sys.param.SysParam.CU_KEY_USERNAME;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.inctech.app.biz.talents.jobseeker.dao.ResumeSQL;
import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;
import cn.inctech.app.biz.talents.jobseeker.service.JobSeekerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/talents")
public class JobseekerAction {

	@RequestMapping("/info/save")
	@ResponseBody
	public Map<String,Object> finishResume(@RequestBody @Valid ResumeModel m){
		Map<String,Object> r=new HashMap<String, Object>();
		log.info(m.toString());
		log.info(rs.insertSQL(m));
		m.setUserid(""+currentUser.get(CU_KEY_USERNAME));
		jss.savetResume(m);
		r.put(RK_MSG, "信息保存成功");
		return r;
	}
	
	@RequestMapping("/info/get")
	@ResponseBody
	public Map<String,Object> getResume(){
		Map<String,Object> r=new HashMap<String, Object>();
		r.put(RK_DATA, jss.getResumeByUserid(""+currentUser.get(CU_KEY_USERNAME)));
		log.info(r.toString());
		return r;
	}
	
	@Resource ResumeSQL rs;
	@Resource Map<String,Object> currentUser;
	@Resource JobSeekerService jss;
	
}
