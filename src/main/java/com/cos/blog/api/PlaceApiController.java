package com.cos.blog.api;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.dto.RequestBodyPlaceDto;
import com.cos.blog.model.Place;
import com.cos.blog.model.PlaceContainer;
import com.cos.blog.service.PlaceService;

@RestController
public class PlaceApiController {

	@Autowired
	private PlaceService placeService;

	// API 요청 방식 : POST, 주소 : /place/show , 설명 : requestbody에
	// String으로 아래 여러가지 값 받아오기
	@PostMapping("/place/show")
	public List<PlaceContainer> placeShow(@RequestBody RequestBodyPlaceDto request) {
		double lat = Double.parseDouble(request.getLat());
		double lng = Double.parseDouble(request.getLng());
		String changing_table_man = request.getChanging_table_man();
		String changing_table_woman = request.getChanging_table_woman();
		String disabled_person = request.getDisabled_person();
		String emergency_bell_disabled = request.getEmergency_bell_disabled();
		String emergency_bell_man = request.getEmergency_bell_man();
		String emergency_bell_woman = request.getEmergency_bell_woman();
		Double leftValue = Double.parseDouble(request.getLeftValue() == null ? "0" : request.getLeftValue());
		Double rightValue = Double.parseDouble(request.getRightValue() == null ? "5" : request.getRightValue() );
		DecimalFormat df = new DecimalFormat("#.#");
		//leftValue = 1 + (leftValue/70);
		leftValue = Double.parseDouble(df.format(leftValue));
		//rightValue = 1.01 + (rightValue/70);
		rightValue = Double.parseDouble(df.format(rightValue));
		Boolean Rated = request.getRated() == "true" ? true : false;
		System.out.println(leftValue+", "+rightValue+", " + Rated);
		return placeService.placeShow(lat, lng, changing_table_man, changing_table_woman, disabled_person,
				emergency_bell_disabled, emergency_bell_man, emergency_bell_woman,
				leftValue, rightValue, Rated);
	}

	// API 요청 방식 : GET, 주소 : /place/search?keyword=${keyword} , 설명 : keyword 값으로 장소

	 @GetMapping("/place/search") 
	 public List<PlaceContainer>
	  placeSearch(@RequestParam("keyword") String keyword,@RequestParam("lat") String latitude, @RequestParam("lng") String longitude) { 
		 return placeService.placeSearch(keyword, latitude , longitude); 
	  }
	 

	 
		/*
		 * @GetMapping("/ipaddress") public String showIp(HttpServletRequest request){
		 * String clientIp = request.getHeader("X-Forwarded-For");
		 * 
		 * // 만약 X-Forwarded-For 헤더가 없으면, 직접 클라이언트의 IP 주소를 얻을 수 있습니다. if (clientIp ==
		 * null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
		 * clientIp = request.getRemoteAddr(); }
		 * 
		 * return "Client IP Address: " + clientIp; }
		 */

	// 백엔드 개발용 , csv파일을 DB에 등록하는 처리
	@GetMapping("/place/add")
	public String placeAdd() {
		String a = "subway";
		String b = "gonggong_hang";
		String c = "starbucks";
		String d = "gonggong_seoul";
		String e = "toilet_list";
		//placeService.placeAdd(a);
	    //placeService.placeAdd(b);
		//placeService.placeAdd(c);
		//placeService.placeAdd(d);
		//placeService.placeAdd(e);
		return "addplace"; // addplace.jsp 결과 페이지로 이동
	}

	// API 요청 방식 : GET, 주소 : /place/delete?id=${id} , 설명 : 댓글id값으로 댓글 삭제
	@GetMapping("/place/delete")
	public void placeDelete(@RequestParam("id") String id) {
		placeService.placeDelete(Integer.parseInt(id));
	}

	// API 요청 방식 : GET, 주소 : /place/detail?id=${id} , 설명 : 특정 place값
	@GetMapping("/place/detail")
	public Place placeDetail(@RequestParam("id") String id) {
		Place place = placeService.placeDetail(Integer.parseInt(id));
		return place;
	}
	
	@GetMapping("/place/speical")
	public List<Place> speical() {
		
		return placeService.speical();
	}

}
