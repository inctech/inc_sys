package cn.inctech.app.biz.demo.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DemoUserMapper {

	@Select("select * from s_user")
	public List<DemoUserModel> queryUsers();
	
	@Select("select * from s_user where userid=#{userid}")
	public DemoUserModel queryUser();
	
	@Insert("insert into s_user(userid,username,password,status,email,descr) values(#{userid},#{username},#{password},#{status},#{email},#{descr})")
	public void insert(DemoUserModel u);
	
	@Insert("update s_user set userid=#{userid},username#{username},password=#{password},status=#{status},email=#{email},descr=#{descr} where userid=#{userid}")
	public void update(DemoUserModel u);
	
}
