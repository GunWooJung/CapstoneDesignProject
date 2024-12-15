package com.cos.blog.api;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.request.RequestPlaceDTO;
import com.cos.blog.dto.response.ResponsePlaceDTO;
import com.cos.blog.handler.LatLngRangeException;
import com.cos.blog.service.PlaceService;
import com.cos.blog.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PlaceApiController {

	// 생성자 주입
	private final PlaceService placeService;

	// id 로 특정 화장실 조회
	@GetMapping("/public/places/{id}")
	public ResponseEntity<ApiResponse<ResponsePlaceDTO>> getOnePlace(@PathVariable(required = true) long id) {

		ResponsePlaceDTO place = placeService.getOnePlace(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", place));
	}

	// keyword가 있는 경우 => 쿼리 스트링으로 검색으로 화장실 목록 가져오기
	// lat => 사용자 위도, lng => 사용자 경도
	@GetMapping("/public/places/search")
	public ResponseEntity<ApiResponse<List<ResponsePlaceDTO>>> getPlacesByKeyword(
			@RequestParam(required = true) String keyword, @RequestParam(required = true) double lat,
			@RequestParam(required = true) double lng) {

		if (lat < -90 || lat > 90) {
			throw new LatLngRangeException("유효하지 않은 위도 값입니다. -90과 90 사이의 값이어야 합니다.");
		}

		if (lng < -180 || lng > 180) {
			throw new LatLngRangeException("유효하지 않은 경도 값입니다. -180과 180 사이의 값이어야 합니다.");
		}

		List<ResponsePlaceDTO> places = placeService.getPlacesByKeyword(keyword, lat, lng);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", places));
	}

	// 현재 자신의 위치와 필터링 조건에 맞는 화장실 목록 가져오기
	// 조회이지만 POST로 처리
	// JPA 반정규화 버전
	@PostMapping("/public/places")
	public ResponseEntity<ApiResponse<List<ResponsePlaceDTO>>> getPlaces(
			@Valid @RequestBody RequestPlaceDTO requestPlaceDTO) {

		List<ResponsePlaceDTO> places = placeService.getPlaces(requestPlaceDTO);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 조회에 성공했습니다.", places));
	}
	/*
	 * // 마이바티스 버전 정규화 버전
	 * 
	 * @PostMapping("/public/version-mapper/places")
	 * //@PostMapping("/public/places") public
	 * ResponseEntity<ApiResponse<List<PlaceMapperDTO>>> getPlacesMapper(
	 * 
	 * @Valid @RequestBody RequestPlaceDTO requestPlaceDTO) { double minLatitude =
	 * 37.4; // 최남단 double maxLatitude = 37.7; // 최북단 double minLongitude = 126.7;
	 * // 최서단 double maxLongitude = 127.2; // 최동단
	 * 
	 * // 랜덤으로 위도와 경도 생성 Random random = new Random();
	 * 
	 * // 서울의 위도 범위 내에서 랜덤 값 생성 double randomLatitude = minLatitude + (maxLatitude -
	 * minLatitude) * random.nextDouble();
	 * 
	 * // 서울의 경도 범위 내에서 랜덤 값 생성 double randomLongitude = minLongitude +
	 * (maxLongitude - minLongitude) * random.nextDouble();
	 * requestPlaceDTO.setLat(randomLatitude);
	 * requestPlaceDTO.setLng(randomLongitude); List<PlaceMapperDTO> places =
	 * placeService.getPlacesMappper(requestPlaceDTO);
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200,
	 * "화장실 조회에 성공했습니다.", places)); }
	 * 
	 * // 마이바티스 버전 반정규화 버전
	 * 
	 * @PostMapping("/public/version-mapper2/places")
	 * //@PostMapping("/public/places") public
	 * ResponseEntity<ApiResponse<List<PlaceMapperDTO>>> getPlacesMapper2(
	 * 
	 * @Valid @RequestBody RequestPlaceDTO requestPlaceDTO) { double minLatitude =
	 * 37.4; // 최남단 double maxLatitude = 37.7; // 최북단 double minLongitude = 126.7;
	 * // 최서단 double maxLongitude = 127.2; // 최동단
	 * 
	 * // 랜덤으로 위도와 경도 생성 Random random = new Random();
	 * 
	 * // 서울의 위도 범위 내에서 랜덤 값 생성 double randomLatitude = minLatitude + (maxLatitude -
	 * minLatitude) * random.nextDouble();
	 * 
	 * // 서울의 경도 범위 내에서 랜덤 값 생성 double randomLongitude = minLongitude +
	 * (maxLongitude - minLongitude) * random.nextDouble();
	 * requestPlaceDTO.setLat(randomLatitude);
	 * requestPlaceDTO.setLng(randomLongitude); List<PlaceMapperDTO> places =
	 * placeService.getPlacesMappper2(requestPlaceDTO);
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200,
	 * "화장실 조회에 성공했습니다.", places)); }
	 */
	/*
	// csv파일을 DB에 등록하는 처리
	@GetMapping("/public/places/enroll")
	public ResponseEntity<ApiResponse<Void>> placeEnroll() throws IllegalStateException, FileNotFoundException {
		placeService.placeEnroll();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "화장실 등록에 성공했습니다.", null));
	}
	*/
}
