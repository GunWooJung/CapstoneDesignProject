package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cos.blog.config.auth.PrincipalDetailService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtSecurityConfig {

    private final PrincipalDetailService principalDetailService;
    private final JwtTokenProvider jwtTokenProvider;

    // AuthenticationManager를 @Bean으로 설정하여 다른 빈에서 주입받을 수 있게 한다.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    // JwtAuthenticationFilter는 authenticationManager를 @Bean으로부터 받아와 사용한다.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtTokenProvider, principalDetailService);
    }

    // 2. BCryptPasswordEncoder 설정
    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    // 3. SecurityFilterChain 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. CSRF 비활성화 및 세션 정책 설정
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 2. 요청 인증 및 권한 설정
        http.authorizeRequests(authorize -> authorize
                .requestMatchers("/static/**"
                		,"/"	
                		,"/health"	
                		, "/login"
                		, "/join"
                        , "/404"
                		, "/places/*"
                		, "/places/*/amend"
                		, "/api/public/**"
                		, "/api/places/*/comments/test"
                		)
                .permitAll().anyRequest().authenticated());

        // 3. 인증 예외 처리
        http.exceptionHandling(exception -> exception.authenticationEntryPoint((request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Authentication required for API\", \"status\":401}");
        }));

        // 4. JWT 필터 추가
        //http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //테스트 중 주석
        return http.build();
    }
}
