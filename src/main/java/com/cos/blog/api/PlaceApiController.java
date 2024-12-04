package com.cos.blog.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestPlaceDTO;
import com.cos.blog.dto.ResponsePlaceDTO;
import com.cos.blog.service.PlaceService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PlaceApiController {

	//생성자 주입
	private final PlaceService placeService;

	// id 로 특정 화장실 조회
	@GetMapping("/places/{placeId}")
	public ResponseEntity<ApiResponse<ResponsePlaceDTO>> getOnePlace(
			@PathVariable(required = true) long placeId) {

		ResponsePlaceDTO place = placeService.getOnePlace(placeId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", place));

	}


	// keyword가 있는 경우 => 쿼리 스트링으로 검색으로 화장실 목록 가져오기
	// lat => 사용자 위도, lng => 사용자 경도
	@GetMapping("/places/search")
	public ResponseEntity<ApiResponse<List<ResponsePlaceDTO>>> getPlaces(
			@RequestParam(required = true) String keyword,
			@RequestParam(required = true) double lat, 
			@RequestParam(required = true) double lng) {

		if (lat < -90 || lat > 90) {
	        throw new IllegalArgumentException("유효하지 않은 위도 값입니다. -90과 90 사이의 값이어야 합니다.");
	    }
	    
	    if (lng < -180 || lng > 180) {
	        throw new IllegalArgumentException("유효하지 않은 경도 값입니다. -180과 180 사이의 값이어야 합니다.");
	    }
		    
		List<ResponsePlaceDTO> places = placeService.getPlacesByKeyword(
				keyword, lat, lng);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", places));
	}

	// 현재 자신의 위치와 필터링 조건에 맞는 화장실 목록 가져오기
	// 조회이지만 POST로 처리
	@PostMapping("/places")
	public ResponseEntity<ApiResponse<List<ResponsePlaceDTO>>> placeShow(
			@Valid @RequestBody RequestPlaceDTO requestPlaceDTO) {
		
		List<ResponsePlaceDTO> places = placeService.getPlaces(requestPlaceDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", places));
	}

	/*
	// csv파일을 DB에 등록하는 처리
	@GetMapping("/places/enroll")
	public ResponseEntity<ApiResponse<Void>> placeEnroll() throws IllegalStateException, FileNotFoundException {

		placeService.placeEnroll();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 등록에 성공했습니다.", null));
	}
	
	//랜덤으로 필터링용 값을 세팅한다.
	@GetMapping("/places/set-value")
	public ResponseEntity<ApiResponse<Void>> setValue() {

		placeService.setValue();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 값 세팅에 성공했습니다.", null));
	}
	*/
}
