package com.cos.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.service.MemberService;
import com.cos.blog.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
		
	private final MemberService memberService;
	
    // 인증 여부 반환, 프로필 이미지 반환
    @GetMapping("/api/auth-check")
    public ResponseEntity<ApiResponse<String>> authCheck(HttpServletRequest request) {
		
    	//로그인 정보로 프로필 이미지 경로를 반환받기
    	String profilePath = memberService.getProfilePath(request);
		
    	if(profilePath == null) {
    		return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(200, "프로필 이미지를 반환", null));
    	}
    	else {
    		return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(200, "프로필 이미지를 반환", profilePath));
    	}
    		
    }
    
    @GetMapping("/health")
    public String health() {
    	return "hello";
    }
}
