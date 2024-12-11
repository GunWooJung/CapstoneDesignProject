package com.cos.blog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cos.blog.dto.request.RequestPlaceDTO;
import com.cos.blog.dto.response.ResponsePlaceDTO;
import com.cos.blog.entity.Place;
import com.cos.blog.handler.InvalidPageException;
import com.cos.blog.handler.NoDataFoundException;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.util.LatLngValue;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceService {
	
	//생성자 주입
	private final PlaceRepository placeRepository;

	// 특정 화장실을 id 로 조회
	public ResponsePlaceDTO getOnePlace(long placeId) {
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
		return ResponsePlaceDTO.toResponsePlaceDTO(place);
	}
	
	// 특정 화장실을 id 로 조회
	public void existOnePlace(long placeId) {
		placeRepository.findById(placeId)
				.orElseThrow(() -> new InvalidPageException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
	}

	// 검색어 없이 주변 화장실 목록 조회
	public List<ResponsePlaceDTO> getPlaces(RequestPlaceDTO requestPlaceDTO) {

		double lat = requestPlaceDTO.getLat();
		double lng = requestPlaceDTO.getLng();
		//내 주변 화장실 목록만 불러오기
		List<Place> places = placeRepository.getPlaces(
				lat - LatLngValue.latDoubleValue,
				lat + LatLngValue.latDoubleValue, 
				lng - LatLngValue.lngDoubleValue, 
				lng + LatLngValue.lngDoubleValue);

		if(places == null ||places.size() == 0)
			throw new NoDataFoundException("데이터 목록이 없습니다.");
		
		// dto로 변환 후 조건에 맞게 필터링
		return places.stream().map((place) -> ResponsePlaceDTO.toResponsePlaceDTO(place))
				.filter(place -> (requestPlaceDTO.isDisabledPerson() ? place.isDisabledPerson() : true ))
				.filter(place -> (requestPlaceDTO.isChangingTableMan() ? place.isChangingTableMan() : true ))
				.filter(place -> (requestPlaceDTO.isChangingTableWoman() ? place.isChangingTableWoman() : true ))
				.filter(place -> (requestPlaceDTO.isEmergencyBellDisabled() ? place.isEmergencyBellDisabled() : true ))
				.filter(place -> (requestPlaceDTO.isEmergencyBellMan() ? place.isEmergencyBellMan() : true ))
				.filter(place -> (requestPlaceDTO.isEmergencyBellWoman() ? place.isEmergencyBellWoman() : true ))
				.filter(place -> (place.getStarAverage() == 0
						|| place.getStarAverage() >= requestPlaceDTO.getLeftValue()))
				.filter(place -> (place.getStarAverage() == 0
						|| place.getStarAverage() <= requestPlaceDTO.getRightValue()))
				.filter(place -> (requestPlaceDTO.isNotRated() ? place.getStarAverage() >= 1 : true))
				.collect(Collectors.toList());
	}

	// 검색어 포함 결과 화장실 목록 조회
	public List<ResponsePlaceDTO> getPlacesByKeyword(
			String keyword, double lat, double lng) {

		List<Place> places = placeRepository.getPlacesByKeyword(keyword);

		if(places == null ||places.size() == 0) 
			throw new NoDataFoundException("데이터 목록이 없습니다.");
		
		// dto로 변환
		// 나에게서 가까운 위치 검색 결과로 마커 이동
		// 거리차로 오름차순 정렬
		return places.stream().map(place -> ResponsePlaceDTO.toResponsePlaceDTO(place))
				.sorted((dto1, dto2) -> Double.compare(
						dto1.distance(lat, lng, dto1.getLat(), dto1.getLng()),
						dto2.distance(lat, lng, dto2.getLat(), dto2.getLng())))
				.collect(Collectors.toList());
	}

	/*
	// 화장실 데이터 DB에 등록
	@Transactional
	public void placeEnroll() throws IllegalStateException, FileNotFoundException {
		String csvFile = "C:\\workspaceProject\\CapstoneDesignProject\\src\\main\\resources\\static\\CSVdata\\gonggong_seoul.csv";
			List<Place> newPlaces = new CsvToBeanBuilder<Place>(new FileReader(csvFile)).withType(Place.class).build()
				.parse();
			
			Random random = new Random(); // Random 객체 생성

		    for (Place place : newPlaces) {
		        // 랜덤한 true/false 값 생성
		        boolean randomBool = random.nextBoolean();
		        place.setDisabledPerson(randomBool); 
		        randomBool = random.nextBoolean();
		        place.setChangingTableMan(randomBool); 
		        randomBool = random.nextBoolean();
		        place.setChangingTableWoman(randomBool); 
		        randomBool = random.nextBoolean();
		        place.setEmergencyBellDisabled(randomBool);
		        randomBool = random.nextBoolean();
		        place.setEmergencyBellMan(randomBool);
		        randomBool = random.nextBoolean();
		        place.setEmergencyBellWoman(randomBool);
		        randomBool = random.nextBoolean();
		        place.setEmergencyBellDisabled(randomBool);
		        place.setOpenTime("항시 개방");
		        
		        placeRepository.save(place); // DB에 저장
		    }	

	} // 모든 place를 불러오기
	*/
}