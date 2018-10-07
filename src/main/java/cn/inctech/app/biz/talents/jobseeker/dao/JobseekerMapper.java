package cn.inctech.app.biz.talents.jobseeker.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.inctech.app.biz.talents.jobseeker.model.ResumeModel;

@Mapper
public interface JobseekerMapper {

	@Insert("insert into b_resume (resid, name, sex, birthday, birthorigin, cardid, mobile, qq, wechat, curlivecity, eduback, jlang, elang, wishindustry, wishcity, qualifications, honorcert, workexper, studyexper, trainexper, interests, selfeval, resmvlink, email,idphoto)\r\n" + 
			"  values (#{resid}, #{name}, #{sex}, #{birthday}, #{birthorigin}, #{cardid}, #{mobile}, #{qq}, #{wechat}, #{curlivecity}, #{eduback}, #{jlang}, #{elang}, #{wishindustry}, #{wishcity}, #{qualifications}, #{honorcert}, #{workexper}, #{studyexper}, #{trainexper}, #{interests}, #{selfeval}, #{resmvlink}, #{email}, #{idphoto,jdbcType=LONGVARBINARY})")
	void _insert_resume(ResumeModel rm);
	
	@InsertProvider(type=cn.inctech.app.biz.talents.jobseeker.dao.ResumeSQL.class,method="insertSQL")
	void insertResume(ResumeModel rm);
	
	@UpdateProvider(type=cn.inctech.app.biz.talents.jobseeker.dao.ResumeSQL.class,method="updateSQL")
	void updateResume(ResumeModel rm);
	
	
}
