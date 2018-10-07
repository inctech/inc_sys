package cn.inctech.app.biz.talents.jobseeker.service;

import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;

public interface JobSeekerService {

	void insertResume(ResumeModel rm);
	
	void updateResume(ResumeModel rm);
	
	void savetResume(ResumeModel rm);
	
}
