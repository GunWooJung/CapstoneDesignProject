package com.cos.blog.api;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.dto.RequestString;
import com.cos.blog.model.Opinion;
import com.cos.blog.model.Report;
import com.cos.blog.repository.OpinionRepository;
import com.cos.blog.service.OpinionService;
import com.cos.blog.service.ReportService;

@RestController
public class ReportApiController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private OpinionService opinionService;
	
	@GetMapping("/opinion/show")
	public List<Opinion> Show() {
		return opinionService.opinionShow();
	}
	
	// API 요청 방식 : GET,  주소 : /report/show?id=${id} , 설명 : place id값으로 
	// 해당 장소 신고 목록 조회
	@GetMapping("/report/show")
	public List<Report> reportShow(@RequestParam("id") int placeId) {
		List<Report> reports = reportService.reportShow(placeId);
		reports.sort(Comparator.comparing(Report::getCreateDate).reversed());
		
		List<Report> firstTwoElements = reports.subList(0, Math.min(reports.size(), 3));
		return firstTwoElements;
	}

	// API 요청 방식 : POST,  주소 : /report/enroll , 설명 : requestbody에
	// String으로 userId, placeId , content 요청으로 신고 등록하기

	@PostMapping("/report/enroll")
	public ResponseEntity<String> reportEnroll(@RequestBody RequestString request,HttpServletRequest requestip) {
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
		String type = "null";
		System.out.println(request.getPlaceId());
		if(request.getDelete_location()!=null && !request.getDelete_location().equals("")) {
			type = "장소 삭제";
			return reportService.reportEnroll(request.getPlaceId(),type,request.getDelete_location(),ipAddress);
		}
		if(request.getBell()!=null&& !request.getBell().equals("")) {
			type = "비상벨 정보 수정";
			return  reportService.reportEnroll(request.getPlaceId(),type,request.getBell(),ipAddress);
		}
		if(request.getDiaper_change()!=null&& !request.getDiaper_change().equals("")) {
			type = "기저귀 교환대 정보 수정";
			return reportService.reportEnroll(request.getPlaceId(),type,request.getDiaper_change(),ipAddress);
		}
		if(request.getDisabled()!=null&& !request.getDisabled().equals("")) {
			type = "장애인 화장실 정보 수정";
			return reportService.reportEnroll(request.getPlaceId(),type,request.getDisabled(),ipAddress);
		}
		if((request.getReport_location_name()!=null && !request.getReport_location_name().equals(""))|| 
				(request.getReport_location_point()!=null&& !request.getReport_location_point().equals(""))) {
			type = "장소명 및 위치";
			String content = "";
			if(request.getReport_location_name()!=null && !request.getReport_location_name().equals("")) {
				content += "장소명 : "+request.getReport_location_name();
				if(request.getReport_location_point()!=null  && !request.getReport_location_point().equals("")) {
					content += ",  위치 : "+request.getReport_location_point();
				}
				return reportService.reportEnroll(request.getPlaceId(),type,content,ipAddress);
			}
			if(request.getReport_location_name() == null || request.getReport_location_name().equals("")) {
				if(request.getReport_location_point()!=null && !request.getReport_location_point().equals("")) {
					content += "위치 : "+request.getReport_location_point();
					return reportService.reportEnroll(request.getPlaceId(),type,content,ipAddress);
				}
			}
		
		}
		if(request.getUser_opinion()!=null&& !request.getUser_opinion().equals("")) {
			return opinionService.saveOpinion(Integer.parseInt(request.getPlaceId()),request.getUser_opinion());
		}
		 return ResponseEntity.status(400).body("fail");
	}
	
	//미개발
	@GetMapping("/report/update")
	public void reportUpdate(@RequestParam("id") int reportId) {
		
	} // post로 수정
	
	// API 요청 방식 : GET,  주소 : /report/delete?id=${id} , 설명 : report id값으로 
	// 해당 신고 내용 삭제
	@GetMapping("/report/delete")
	public void reportDelete(@RequestParam("id") int reportId) {
		reportService.reportDelete(reportId);
	}

	// API 요청 방식 : GET,  주소 : /report/clickheart?id=${id} , 설명 : report id값으로 
	// 하트를 누르면 신고 count값이 1 증가
	@GetMapping("/report/clickheart")
	public ResponseEntity<String> reportClickHeart(@RequestParam("id") int reportId, @RequestParam("placeid") int placeId,HttpServletRequest requestip) {
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
	
		return reportService.reportClickHeart(reportId,placeId, ipAddress);
	}
}
