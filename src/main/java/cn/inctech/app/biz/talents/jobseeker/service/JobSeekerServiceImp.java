package cn.inctech.app.biz.talents.jobseeker.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.inctech.app.biz.talents.jobseeker.dao.JobseekerMapper;
import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;

@Service
public class JobSeekerServiceImp implements JobSeekerService {

	@Override
	public void insertResume(ResumeModel rm) {
		jsm.insertResume(rm);
	}

	@Override
	public void updateResume(ResumeModel rm) {
		jsm.updateResume(rm);
	}

	
	@Override
	public void savetResume(ResumeModel rm) {
		if(jsm.isExistResumeForUserid(rm.getUserid()))
			jsm.updateResume(rm);
		else
			jsm.insertResume(rm);
	}
	
	@Override
	public ResumeModel getResumeByUserid(String userid) {
		return jsm.getResumeByUserid(userid);
	}
	
	@Resource JobseekerMapper jsm;

}
