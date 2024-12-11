package com.cos.blog.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    // 인증 여부 반환
    @GetMapping("/api/auth-check")
    public String authCheck() {
    	return "success";
    }
}
