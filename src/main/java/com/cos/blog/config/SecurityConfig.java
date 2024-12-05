package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.handler.LoginFailureHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private LoginFailureHandler loginFailureHandler; // 로그인 실패 핸들러 주입
	  
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 방식 설정
    }
	  
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // CSRF 비활성화 (필요에 따라 활성화)
						.authorizeHttpRequests()
						.requestMatchers("/static/**", "/join", "/test" , "/api/members/**") // 정적 리소스 경로 및 허용된 URL
				        .permitAll()  // 해당 경로는 누구나 접근 가능
				        .anyRequest().authenticated() // 그 외의 모든 요청은 인증 필요
						.and()
				.formLogin(form -> form.loginPage("/login") // 사용자 정의 로그인 페이지 설정
						.loginProcessingUrl("/api/members/login") // 로그인 처리 URL
						.failureHandler(loginFailureHandler)//로그인 실패 시 처리하는 핸들러 등록.
						.usernameParameter("loginId") // 로그인 아이디 파라미터 이름
						.passwordParameter("password") // 비밀번호 파라미터 이름
						.permitAll() // 로그인 페이지 접근은 누구나 가능
				).logout(logout -> logout.logoutUrl("/logout") // 로그아웃 URL
						.logoutSuccessUrl("/login") // 로그아웃 후 리디렉션할 URL
						.permitAll() // 로그아웃은 누구나 가능
				);

		return http.build(); // SecurityFilterChain 반환
	}
}
