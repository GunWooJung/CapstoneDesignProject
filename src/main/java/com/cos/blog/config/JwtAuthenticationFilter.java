package com.cos.blog.config;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalDetailService principalDetailService;

    // 생성자 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, PrincipalDetailService principalDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.principalDetailService = principalDetailService;
    }

    // @PostConstruct를 통해 initialization 후 필터 설정
    @PostConstruct
    public void init() {
        // 이곳에서 AuthenticationManager를 설정할 수 있습니다.
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 요청에서 JWT 토큰 추출
        String token = getTokenFromRequest(request);

        // 토큰이 존재하고 유효하면
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰에서 사용자 정보 추출
            String username = jwtTokenProvider.getUsernameFromToken(token);
            PrincipalDetail userDetails = (PrincipalDetail) principalDetailService.loadUserByUsername(username);

            // 인증 객체 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // WebAuthenticationDetailsSource를 통해 세부 정보 설정
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 인증 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // 요청 헤더에서 JWT 토큰을 추출하는 메서드
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // "Bearer "로 시작하는지 확인하고 그 뒤의 토큰을 반환
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

