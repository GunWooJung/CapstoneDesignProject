package com.cos.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.CommentService;
import com.cos.blog.service.ReportService;
import com.cos.blog.service.StarRatingService;


@Controller
public class MainController {

	
	@GetMapping("/test")
	public String test2() {
		return "index";
	}
	
	@GetMapping("/")
	public String test3() {
		return "index";
	}
	
	@GetMapping("/viewdetails")
	public String viewdetails() {
		return "view_details";
	}

	@GetMapping("/amend_information")
	public String amend_information() {
		return "amend_information";
	}
	


}
