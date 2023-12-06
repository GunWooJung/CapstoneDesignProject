package com.cos.blog.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestBodyCommentDto;
import com.cos.blog.dto.RequestBodyReportDto;
import com.cos.blog.dto.RequestPassword;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.Comment;
import com.cos.blog.model.Report;
import com.cos.blog.service.CommentService;
import com.cos.blog.service.PlaceService;
import com.cos.blog.service.ReportService;
import com.cos.blog.service.StarRatingService;

@RestController
public class ComementApiController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private PlaceService placeService;
	
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private StarRatingService starRatingService;
	
	@GetMapping("/allsetnew")
	public String AllNew() {
		placeService.DeleteAll();
		reportService.DeleteAll();
		commentService.DeleteAll();
		starRatingService.DeleteAll();
		return "delete success";
	}
	
	// API 요청 방식 : GET,  주소 : /comment/show?id=${id} , 설명 : place id 값으로 해당 장소 댓글 불러오기
	@GetMapping("/comment/show")
	public List<Comment> commentShow(@RequestParam("id") int placeId) {
		List<Comment> comments = commentService.commentShow(placeId);
		return comments;
	}
	
	// API 요청 방식 : POST,  주소 : /comment/enroll , 설명 : requestbody에 
	// String으로 userId , placeId, score, content 받아오기
	// 댓글 등록시 place의 평균 score값이 자동으로 갱신됨
	@PostMapping("/comment/enroll")
	public ResponseEntity<String> commentEnroll(@RequestBody RequestBodyCommentDto request,HttpServletRequest requestip) {
		 String ipAddress = requestip.getHeader("X-Forwarded-For");
		  if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			  ipAddress = requestip.getHeader("Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			    ipAddress = requestip.getHeader("WL-Proxy-Client-ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			    ipAddress = requestip.getHeader("HTTP_CLIENT_ipAddress");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			    ipAddress = requestip.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			    ipAddress = requestip.getRemoteAddr();
			}
		return commentService.commentEnroll(request.getUsername(),request.getPassword(),request.getPlaceId(),request.getContent(),ipAddress);
	}
	
	//아직 미개발
	@GetMapping("/comment/update")
	public void commentUpdate(@RequestParam("id") int commentId) {
		
	}
	// API 요청 방식 : GET,  주소 : /comment/delete?id=${id} , 설명 :comment id 값으로 해당 댓글 삭제
	@PostMapping("/comment/delete")
	public ResponseEntity<String> commentDelete(@RequestBody RequestPassword request,@RequestParam("id") int commentId) {
		int placeId = Integer.parseInt(request.getPlaceId());
		String password = request.getPassword();
		return commentService.commentDelete(placeId,commentId,password);
	}
	
	
}
