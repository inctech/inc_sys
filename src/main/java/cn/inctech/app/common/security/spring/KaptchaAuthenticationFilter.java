package cn.inctech.app.common.security.spring;

import static cn.inctech.app.common.cfg.param.GlobalConfig.*;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class KaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
    public KaptchaAuthenticationFilter(String servletPath, String failureUrl) {
        super(servletPath);
        this.servletPath = servletPath;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.debug("["+servletPath+"]"+"method is:"+req.getMethod()+"--servletPath is:"+req.getServletPath());
        if ("POST".equalsIgnoreCase(req.getMethod()) && servletPath.equals(req.getServletPath())) {
            String expect = (String) req.getSession().getAttribute(skey);
            if (expect != null && !expect.equalsIgnoreCase(req.getParameter(param_name))) {
                unsuccessfulAuthentication(req, res, new InsufficientAuthenticationException(RESULT_MSG_VALID_CODE_ERR));
                return;
            }
        }
        chain.doFilter(request, response);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
    
    final Logger log = LoggerFactory.getLogger(this.getClass());
    String servletPath;
    @Value("${kaptcha.input.name}") String param_name="image_kaptcha";
    @Value("${kaptcha.session.key}") String skey="kaptcha.code";
}
