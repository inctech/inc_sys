package cn.inctech.app.common.cfg.bean;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(3000);
        requestFactory.setConnectTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        List<MediaType> smt=new ArrayList<>();
        smt.add(MediaType.TEXT_HTML);
        smt.add(MediaType.APPLICATION_JSON_UTF8);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter smc=new StringHttpMessageConverter(Charset.forName("UTF-8"));
        smc.setSupportedMediaTypes(smt);
        messageConverters.add(smc);
        MappingJackson2HttpMessageConverter jmc=new MappingJackson2HttpMessageConverter();
        jmc.setSupportedMediaTypes(smt);
        messageConverters.add(jmc);
        restTemplate.setMessageConverters(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
	
}
