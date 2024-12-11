package com.cos.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.request.RequestStarRatingDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.service.MemberService;
import com.cos.blog.service.StarRatingService;
import com.cos.blog.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StarRatingApiController {

	private final MemberService memberService;
	//생성자 주입
	private final StarRatingService starRatingtService;

	// 별점 등록하기
	@PostMapping("/star-rating")
	public ResponseEntity<ApiResponse<Void>> enroll(
			@Valid	@RequestBody RequestStarRatingDTO requestStarRatingDTO) {
		
		PrincipalDetail principalDetail = memberService.getLoggedInUserDetails();
		
		// 별점 등록
		if(principalDetail == null) throw new UnauthorizedAccessException("비로그인 입니다.");
		
		Member member = principalDetail.getMember();

		starRatingtService.enroll(member, requestStarRatingDTO.getPlaceId(), requestStarRatingDTO.getScore());

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "별점 등록에 성공했습니다.", null));
	}

}
