package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
		.addResourceLocations("classpath:/static/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 특정 URL 패턴에 대한 CORS 설정
		registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
				.allowedOrigins("http://localhost:8080", "https://cap.30ticket.store") // 허용할 출처 (예: React 개발 서버)
				.allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
				.allowedHeaders("*") // 허용할 헤더
				.allowCredentials(true); // 자격 증명 포함 허용
	}
}
