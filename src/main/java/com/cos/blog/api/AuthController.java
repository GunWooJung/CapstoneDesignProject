package com.cos.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.NaverConfiguration;
import com.cos.blog.entity.Member;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.service.MemberService;
import com.cos.blog.storage.NCPObjectStorageService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final NaverConfiguration naverConfiguration;
	
	private final MemberService memberService;
	
    // 인증 여부 반환, 프로필 이미지 반환
    @GetMapping("/api/auth-check")
    public ResponseEntity<ApiResponse<String>> authCheck() {
		//로그인 정보 꺼내기
		Member member = memberService.getLoggedInUserDetails();
		
		if(member == null)
			throw new UnauthorizedAccessException("사용자를 찾을 수 없습니다.");
			
		if(member.getImageFileName() != null) {	//프사 보유
			
			String path = naverConfiguration.getEndPoint()+"/"+
					naverConfiguration.getBucketName()+"/"+
					naverConfiguration.getDirectoryPath()+
					member.getImageFileName();
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(200, "프로필 이미지를 반환", path));
		}
		else	//프사 미보유
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>(200, "프로필 이미지를 반환", null));
    }
    
    @GetMapping("/health")
    public String health() {
    	return "hello";
    }
}
