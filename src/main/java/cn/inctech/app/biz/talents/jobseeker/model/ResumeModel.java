package cn.inctech.app.biz.talents.jobseeker.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ResumeModel {

	private Integer resid;//简历id
	@NotBlank(message="姓名不能为空") private String name;            
	@NotBlank(message="性别不能为空")  private String sex;             
	/*@NotBlank(message="出生日期不能为空")*/ @DateTimeFormat(pattern = "yyyy-MM-dd") private Date birthday;        
	private String birthorigin;
	@NotBlank(message="身份证号不能为空") private String cardid;  
	@NotBlank(message="手机不能为空") private String mobile;          
	private String qq;              
	@NotBlank(message="微信号不能为空")private String wechat;
	/*@NotBlank(message="邮箱不能为空")*/private String email;//
	private String curlivecity;//现居住城市
	private String eduback;//教育经历      
	private String jlang;//日语等级
	private String elang;//英语等级
	@NotBlank(message="微信号不能为空")private String wishindustry;//希望从事行业
	@NotBlank(message="期望工作地点不能为空")private String wishcity;//希望工作地点        
	private String qualifications;//资格认定
	private String honorcert;//荣誉称号
	private String workexper;//工作经历     
	private String studyexper;//留学经历    
	private String trainexper;//培训经历
	private String interests;//爱好兴趣    
	@NotBlank(message="自我评价不能为空")private String selfeval;//自我评价     
	private String resmvlink;//简历视频链接      
	private byte[] idphoto;//
	private String userid;//外键
	
	/*private String langt;//外语种类       
	private String langlvl;//外语等级*/
	
}
