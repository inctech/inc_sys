package cn.inctech.app.common.cfg.bean;

import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_CSS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_FAVICON;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_FONTS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_JS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_TEST;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_ALLOW_USER_SELF_REGIST;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_ERROR;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_PAGE;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_PASS_ERROR;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGIN_SUCCESS;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_LOGOUT;
import static cn.inctech.app.common.cfg.param.GlobalConfig.URL_SMS_CODE;
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
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	if(use_kaptcha) {
    		KaptchaAuthenticationFilter kaf=new KaptchaAuthenticationFilter(URL_LOGIN, URL_LOGIN_ERROR);
    		kaf.setParam_name(param_name);
    		kaf.setSkey(skey);
    		http.addFilterBefore(kaf, UsernamePasswordAuthenticationFilter.class)/*.csrf().disable()*/;
    	}
		http
            .authorizeRequests().antMatchers(kaptcha_url,URL_ALLOW_CSS, URL_ALLOW_JS, URL_ALLOW_FONTS, URL_ALLOW_FAVICON,URL_LOGIN,URL_LOGIN_PAGE,URL_ALLOW_TEST,URL_SMS_CODE+"/*",URL_ALLOW_USER_SELF_REGIST).permitAll()
            .and().csrf().disable()
            .formLogin().loginProcessingUrl(URL_LOGIN)
            .loginPage(URL_LOGIN_PAGE).failureUrl(URL_LOGIN_PASS_ERROR)//不指定地址直接奔/login?error method='post'
            .permitAll()
            .successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
                	String cur_user=auth.getName();
                	log.debug("[current user:]"+cur_user+" has logged in"+auth.getAuthorities());
                	for(Object o:auth.getAuthorities())
                		log.info("auth is:"+o);
                	currentUser.put(CU_KEY_USERNAME, cur_user);
                	currentUser.put(CU_KEY_USERROLE, auth.getAuthorities());
                	request.getRequestDispatcher(URL_LOGIN_SUCCESS).forward(request, response);
                }
            })
            .and().rememberMe().key(KEY) // 启用 remember me
            .and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response,AccessDeniedException e) throws IOException, ServletException {
					log.info("[username is:] "+currentUser.get(CU_KEY_USERNAME)+", [auth is:] "+currentUser.get(CU_KEY_USERROLE)+":"+request.getRequestURI());
					log.info(e.getMessage());
					//e.printStackTrace();
				}
            }).accessDeniedPage("/403")
            .and().logout().logoutUrl(URL_LOGOUT).logoutSuccessUrl(URL_LOGIN_PAGE)
            .invalidateHttpSession(true);
		http.headers().frameOptions().sameOrigin(); // 允许来自同一来源的 控制台的请求
        http.csrf().ignoringAntMatchers(URL_LOGIN+"*",SYS_PATH+"/**",SUB_APP_PATH_TALENTS+"/**");
        
        http.authorizeRequests().antMatchers("/sys/userinfo").hasAnyAuthority("jobseeker")
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
