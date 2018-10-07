package cn.inctech.app.common.cfg.bean;

import static cn.inctech.app.common.cfg.param.GlobalConfig.*;
import static cn.inctech.app.sys.param.SysParam.CU_KEY_USERNAME;
import static cn.inctech.app.sys.param.SysParam.CU_KEY_USERROLE;
import static cn.inctech.app.sys.param.SysParam.SUB_APP_PATH_TALENTS;
import static cn.inctech.app.sys.param.SysParam.SYS_PATH;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cn.inctech.app.common.security.spring.KaptchaAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(sql_query_user_by_name)
		.authoritiesByUsernameQuery(sql_query_auth_by_name)
		.passwordEncoder(passwordEncoder);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		if(use_kaptcha) {
    		KaptchaAuthenticationFilter kaf=new KaptchaAuthenticationFilter(U_LOGIN, U_LOGIN_ERROR);
    		kaf.setParam_name(param_name);
    		kaf.setSkey(skey);
    		http.addFilterBefore(kaf, UsernamePasswordAuthenticationFilter.class);
    	}
		http.authorizeRequests()
		.antMatchers(kaptcha_url,UA_CSS, UA_JS, UA_FONTS, UA_FAVICON,U_LOGIN,U_LOGOUT,U_LOGIN_PAGE,UA_TEST,U_SMS_CODE+"/*",U_SYS_CODE+"/*",UA_USER_REGIST).permitAll()
		.antMatchers("/","/sys/userinfo","/sys/logout","/talents/**").hasAnyAuthority("jobseeker")
		.antMatchers("/**").hasAnyAuthority("admin")
		.anyRequest().fullyAuthenticated()
		.and().formLogin().successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
            	String cur_user=auth.getName();
            	log.debug("[current user:]"+cur_user+" has logged in"+auth.getAuthorities());
            	for(Object o:auth.getAuthorities())
            		log.info("auth is:"+o);
            	currentUser.put(CU_KEY_USERNAME, cur_user);
            	currentUser.put(CU_KEY_USERROLE, auth.getAuthorities());
            	request.getRequestDispatcher(U_LOGIN_SUCCESS).forward(request, response);
            }
        })
		.loginPage(U_LOGIN).loginPage(U_LOGIN_PAGE).failureUrl(U_LOGIN_PASS_ERROR)
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher(U_LOGOUT)).logoutSuccessUrl("/").invalidateHttpSession(true)
		.and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException e) throws IOException, ServletException {
				log.info("[username is:] "+currentUser.get(CU_KEY_USERNAME)+", [auth is:] "+currentUser.get(CU_KEY_USERROLE)+":"+request.getRequestURI()+":"+e.getMessage());
				//e.printStackTrace();
			}
        }).accessDeniedPage("/403");
		
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的 控制台的请求
		//http.csrf().disable();
        http.csrf().ignoringAntMatchers(U_LOGIN+"*",SYS_PATH+"/**",U_LOGOUT+"*");
	}
	
    protected void xxconfigure(HttpSecurity http) throws Exception {
    	if(use_kaptcha) {
    		KaptchaAuthenticationFilter kaf=new KaptchaAuthenticationFilter(U_LOGIN, U_LOGIN_ERROR);
    		kaf.setParam_name(param_name);
    		kaf.setSkey(skey);
    		http.addFilterBefore(kaf, UsernamePasswordAuthenticationFilter.class)/*.csrf().disable()*/;
    	}
		http
            .authorizeRequests().antMatchers(kaptcha_url,UA_CSS, UA_JS, UA_FONTS, UA_FAVICON,U_LOGIN,/*U_LOGOUT,*/U_LOGIN_PAGE,UA_TEST,U_SMS_CODE+"/*",UA_USER_REGIST).permitAll()
            //.and().csrf().disable()
            .and().formLogin()
            .successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
                	String cur_user=auth.getName();
                	log.debug("[current user:]"+cur_user+" has logged in"+auth.getAuthorities());
                	for(Object o:auth.getAuthorities())
                		log.info("auth is:"+o);
                	currentUser.put(CU_KEY_USERNAME, cur_user);
                	currentUser.put(CU_KEY_USERROLE, auth.getAuthorities());
                	request.getRequestDispatcher(U_LOGIN_SUCCESS).forward(request, response);
                }
            })
            .loginProcessingUrl(U_LOGIN)
            .loginPage(U_LOGIN_PAGE).failureUrl(U_LOGIN_PASS_ERROR)//不指定地址直接奔/login?error method='post'
            .permitAll()
            .and().logout().logoutUrl(U_LOGOUT).logoutSuccessUrl(U_LOGIN_PAGE).permitAll().invalidateHttpSession(true)
            .and().rememberMe().key(KEY) // 启用 remember me
            .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException e) throws IOException, ServletException {
					log.info("[username is:] "+currentUser.get(CU_KEY_USERNAME)+", [auth is:] "+currentUser.get(CU_KEY_USERROLE)+":"+request.getRequestURI()+":"+e.getMessage());
					/*log.info(url+" : "+e.getMessage());
					e.printStackTrace();*/
				}
            }).accessDeniedPage("/403");
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的 控制台的请求
        http.csrf().ignoringAntMatchers(U_LOGIN+"*",SYS_PATH+"/**",SUB_APP_PATH_TALENTS+"/**");
        
        http.authorizeRequests().antMatchers("/","/sys/userinfo").hasAnyAuthority("jobseeker")
        .antMatchers("/**").hasAnyAuthority("admin");
        
    }
    
    static final String KEY = "inctech.cn";
    
    @Value("${webapp.content_type.json}") String json_content_type;
    @Value("${kaptcha.chkurl:/sys/img_code}") String kaptcha_url;
    @Value("${webapp.use_kaptcha}") boolean use_kaptcha=true;
    @Value("${kaptcha.input.name:image_kaptcha}") String param_name;
    @Value("${kaptcha.session.key:kaptcha_session_code}") String skey;
    
    @Value("${webapp.security.jdbc.sql_query_user_by_name:select userid,password,true from v_user where userid =?}") String sql_query_user_by_name;
    @Value("${webapp.security.jdbc.sql_query_auth_by_name:select userid username,authority from v_userauth where userid=?}") String sql_query_auth_by_name;
    
    @Resource PasswordEncoder passwordEncoder;
    @Resource DataSource dataSource;
    @Resource Map<String,Object> currentUser;
    
}
