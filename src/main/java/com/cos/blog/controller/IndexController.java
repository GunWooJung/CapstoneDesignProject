package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

	// 홈화면
	@GetMapping({"/", "{}"})
	public String index() {
		return "index";
	}
	
	// 회원가입 경로
	@GetMapping("/join")
	public String join() {
		return "join";
	}

	//로그인 경로
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	// 장소에 대한 상세 페이지 
	@GetMapping("/places/{id}")
	public String places(@PathVariable long id) {
		return "view_details";
	}

	// 장소에 대한 정보 수정 제안 페이지
	@GetMapping("/places/{id}/amend")
	public String amendInformation(@PathVariable long id) {
		return "amend_information";
	}
	


}
