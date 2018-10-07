package cn.inctech.app.sys.general.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.inctech.app.sys.general.model.UserModel;

@Mapper
public interface GeneralMapper {

	@Insert("insert into b_user(userid,username,password,mobile,email,status,descr) values(#{userid},#{userid},#{password},#{mobile},#{email},true,#{descr})")
	public void registUser(UserModel user);
	
	@Select("select count(*) from b_user u where u.userid=#{userid} or u.mobile=#{mobile}")
	public int queryBUserByUseridOrMobile(UserModel u);
	
	@Select("select * from b_user where userid=#{userid}")
	public UserModel queryUserById(String userid);
	
	@UpdateProvider(type=cn.inctech.app.sys.general.sql.UserSQL.class,method="updateSQL")
	public void updateUser(UserModel user);
	
	@Update("update t_resume set mobile=#{mobile} where userid=#{userid}")
	public int updateMobileByUserid(String userid,String mobile);
}
