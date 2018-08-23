package cn.inctech.app.sys.bean;

import static cn.inctech.app.common.cfg.param.GlobalConfig.*;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.inctech.app.common.security.spring.KaptchaAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    
    /*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();// 使用 BCrypt 加密
    }
    
    @Bean
    UserDetailsService ucJdbcUserService(){
        return new UserDetailsServiceBean();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); // 设置密码加密方式
        return authenticationProvider;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(SQL_QUERY_USER_BY_NAME)
		.authoritiesByUsernameQuery(SQL_QUERY_AUTH_BY_NAME)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new KaptchaAuthenticationFilter(URL_LOGIN, URL_LOGIN_ERROR), UsernamePasswordAuthenticationFilter.class) //在认证用户名之前认证验证码，如果验证码错误，将不执行用户名和密码的认证
                .authorizeRequests().antMatchers(kaptcha_url,URL_ALLOW_CSS, URL_ALLOW_JS, URL_ALLOW_FONTS, URL_ALLOW_INDEX,URL_LOGIN,URL_LOGIN_PAGE).permitAll()
                .and()
                .formLogin()
                //.loginProcessingUrl(URL_LOGIN)
                .loginPage(URL_LOGIN_PAGE)
                .failureUrl(URL_LOGIN_PASS_ERROR)//不指定地址直接奔/login?error method='post'
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
                    	request.getRequestDispatcher(URL_LOGIN_SUCCESS).forward(request, response);
                    }
                })
                .and().rememberMe().key(KEY) // 启用 remember me
                .and().exceptionHandling().accessDeniedPage("/403")   // 拒绝访问就重定向到 403 页面
                .and().logout().logoutUrl(URL_LOGOUT).logoutSuccessUrl(URL_LOGIN);
        
        http.authorizeRequests().antMatchers("/**").hasRole("admin");
        
        //人力项目配置
		http.authorizeRequests().antMatchers("/talents/student/**").hasAnyRole("student", "admin")
				.antMatchers("/talents/company/**").hasAnyRole("company", "admin").antMatchers("/talents/teacher/**")
				.hasAnyRole("teacher", "admin");
        
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.csrf().ignoringAntMatchers("/ajax/**");
        http.csrf().ignoringAntMatchers("/upload");
        http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的 控制台的请求
        
    }
    
    
    final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${webapp.content_type.json}") String json_content_type;
    @Value("${kaptcha.chkurl}") String kaptcha_url;
    static final String KEY = "inctech.cn";
    /* @Autowired UserDetailsService userService;
    @Autowired PasswordEncoder passwordEncoder;*/
    @Resource DataSource dataSource;
    
    static final String SQL_QUERY_USER_BY_NAME="select userid,password,‘1’ from select * from s_user where userid =?";
    static final String SQL_QUERY_AUTH_BY_NAME="select u.userid username,r.rolename as authority from s_user u join s_user_role ur on u.userid=ur.userid join s_role r on r.roleid=ur.roleid where u.userid=?";
}
