package cn.inctech.app.common.cfg.bean;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import cn.inctech.app.common.mvc.SysMvcInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(false).maxAge(3600);
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(sysMvcInterceptor());
        addInterceptor.excludePathPatterns("/sys**");
        addInterceptor.excludePathPatterns("/login**");
        addInterceptor.addPathPatterns("/talents/**");
    }
	
	@Bean 
	public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
	
	@Bean 
	public SysMvcInterceptor sysMvcInterceptor(){
        return new SysMvcInterceptor();
    }
	
	@Bean
	public ServletRegistrationBean<Servlet> servletRegistrationBean() throws ServletException {
		ServletRegistrationBean<Servlet> servlet = new ServletRegistrationBean<Servlet>(new KaptchaServlet(), chk_url);
		servlet.addInitParameter("kaptcha.border", "no");// 无边框
		servlet.addInitParameter("kaptcha.session.key", skey);// session key
		servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor);
		servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);
		servlet.addInitParameter("kaptcha.obscurificator.impl", obscurificator);
		servlet.addInitParameter("kaptcha.noise.impl", noise);
		servlet.addInitParameter("kaptcha.image.width", width);
		servlet.addInitParameter("kaptcha.image.height", height);
		servlet.addInitParameter("kaptcha.textproducer.char.length", clength);
		servlet.addInitParameter("kaptcha.textproducer.char.space", cspace);
		servlet.addInitParameter("kaptcha.background.clear.from", from); // 和登录框背景颜色一致
		servlet.addInitParameter("kaptcha.background.clear.to", to);
		return servlet;
	}
	
	@Value("${kaptcha.chkurl}") String chk_url;
	@Value("${kaptcha.border}") private String kborder;
	@Value("${kaptcha.session.key}")private String skey;
	@Value("${kaptcha.textproducer.font.color}")private String fcolor;
	@Value("${kaptcha.textproducer.font.size}")private String fsize;
	@Value("${kaptcha.obscurificator.impl}")private String obscurificator;
	@Value("${kaptcha.noise.impl}")private String noise;
	@Value("${kaptcha.image.width}")private String width;
	@Value("${kaptcha.image.height}")private String height;
	@Value("${kaptcha.textproducer.char.length}")private String clength;
	@Value("${kaptcha.textproducer.char.space}")private String cspace;
	@Value("${kaptcha.background.clear.from}")private String from;
	@Value("${kaptcha.background.clear.to}")private String to;

}
