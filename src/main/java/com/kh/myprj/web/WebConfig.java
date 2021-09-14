package com.kh.myprj.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.myprj.web.interceptor.LoginCheckinterceptor;
import com.kh.myprj.web.interceptor.MeasuringInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new MeasuringInterceptor())
//						.order(1)
//						.addPathPatterns("/**");
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginCheckinterceptor())
						.order(1)
						//포함할 url
						.addPathPatterns("/**")
						//제외할 url
						.excludePathPatterns("/",
																"/login",
																"/logout",
																"/members/join",
																"/help/**",
																"/css/**",
																"/js/**",
																"/img/**",
																"/webjars/**",
																"/error/**");
	}
}
