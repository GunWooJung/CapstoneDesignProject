package com.cos.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.service.HeartService;
import com.cos.blog.service.MemberService;
import com.cos.blog.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HeartApiController {

	private final MemberService memberService;
	//생성자 주입
	private final HeartService heartService;

	// 특정 신고에 대해 하트 + 1 업데이트 수행
	@PutMapping("/places/{placeId}/reports/{reportId}/heart")
	public ResponseEntity<ApiResponse<Void>>  reportHeartClick(
			HttpServletRequest request,
			@PathVariable(required = true) long placeId,
			@PathVariable(required = true) long reportId) {
		
		heartService.clickHeart(request, placeId, reportId);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(200, "신고 횟수가 증가했습니다.", null));
	}
	
}
