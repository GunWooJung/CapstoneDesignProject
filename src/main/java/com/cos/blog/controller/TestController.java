package com.cos.blog.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;

@RestController
public class TestController {
	
	@GetMapping("/test")
	public String join(@AuthenticationPrincipal PrincipalDetail principalDetail) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
		    System.out.println("Authenticated user: " + authentication.getName());
		}
		return "s";
	}

}
