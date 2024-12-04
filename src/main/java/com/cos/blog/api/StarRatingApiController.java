package com.cos.blog.api;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestStarRatingDTO;
import com.cos.blog.service.StarRatingService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StarRatingApiController {

	//생성자 주입
	private final StarRatingService starRatingtService;

	// 별점 등록하기
	@PostMapping("/star-rating")
	public ResponseEntity<ApiResponse<Void>> enroll(
			@Valid	@RequestBody RequestStarRatingDTO requestStarRatingDTO) {
		// 별점 등록
		starRatingtService.enroll(requestStarRatingDTO.getPlaceId(), requestStarRatingDTO.getScore());

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "별점 등록에 성공했습니다.", null));
	}

}
