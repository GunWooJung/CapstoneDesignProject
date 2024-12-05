package com.cos.blog.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    	 // 인증 예외 확인
        String errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";  // 기본 오류 메시지
        
        // exception에 따른 상세한 오류 메시지 처리
        if (exception.getClass().getSimpleName().equals("BadCredentialsException")) {
            errorMessage = "잘못된 아이디 또는 비밀번호입니다.";
        } else if (exception.getClass().getSimpleName().equals("LockedException")) {
            errorMessage = "계정이 잠겨 있습니다. 관리자에게 문의하세요.";
        } else if (exception.getClass().getSimpleName().equals("DisabledException")) {
            errorMessage = "계정이 비활성화되었습니다.";
        } else if (exception.getClass().getSimpleName().equals("AccountExpiredException")) {
            errorMessage = "계정이 만료되었습니다.";
        }

        // 세션에 오류 메시지 설정
        request.getSession().setAttribute("error", errorMessage); 
        
        // 콘솔에 오류 메시지 출력 (디버깅용)
        System.out.println("Login Failure: " + errorMessage);

        // 로그인 실패 시 /login?error 로 리다이렉트
        response.sendRedirect("/login?error");
    }
}