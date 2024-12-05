package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

import lombok.RequiredArgsConstructor;

//1. 어노테이션 제거
@Configuration
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@RequiredArgsConstructor
public class SecurityConfig{ // 2. extends 제거

	// 3. principalDetailService 제거
	private final PrincipalDetailService principalDetailService;
	
	// 4. AuthenticationManager 메서드 생성
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean // IoC가 되요!!
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 5. 기본 패스워드 체크가 BCryptPasswordEncoder 여서 설정 필요 없음.



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    		// 1. csrf 비활성화
			http.csrf().disable();

			// 2. 인증 주소 설정
			http.authorizeRequests(
					authorize -> authorize.requestMatchers("/static/**", "/login", "/join", "/api/members/**").permitAll()
							.anyRequest().authenticated()
			);
			
			// 3. 로그인 처리 프로세스 설정
			http.formLogin(f -> f.loginPage("/login")
					.loginProcessingUrl("/api/members/login")
					.defaultSuccessUrl("/")
					.successHandler((request, response, authentication) -> {
			            // 로그인 성공 후 추가 작업
			            // 예: 성공 로그 메시지, 사용자 정보 저장 등
			            System.out.println("로그인 성공: " + authentication.getName());
			        })
					.failureUrl("/login?error") // 로그인 실패 시 리디렉션할 URL
			        .failureHandler((request, response, exception) -> {
			            // 로그인 실패 후 추가 작업
			            // 예: 실패 로그 메시지, 사용자에게 실패 이유 전달 등
			            System.out.println("로그인 실패: " + exception.getMessage());
			            request.getSession().setAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
			        })
			);
 
        return http.build(); // `SecurityFilterChain` 반환
    }
}
