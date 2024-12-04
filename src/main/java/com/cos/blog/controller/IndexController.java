package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

	// 정적 리소스 루트 경로 static/index.html
	@GetMapping("/")
	public String index() {
		return "forward:/index.html";
	}
	
	// 장소에 대한 상세 페이지 
	@GetMapping("/places/{id}")
	public String places(@PathVariable long id) {
		return "forward:/view_details.html";
		// static 안에 html 파일로 포워딩
	}

	// 장소에 대한 정보 수정 제안 페이지
	@GetMapping("/places/{id}/amend")
	public String amendInformation(@PathVariable long id) {
		return "forward:/amend_information.html";
	}
	


}
