package com.cos.blog.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.request.RequestReportDTO;
import com.cos.blog.dto.response.ResponseReportDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.service.ReportService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReportApiController {

	private final ReportService reportService;
	
	// 해당 장소 최근 신고 목록 조회(개발자 의견은 빼고)
	@GetMapping("/places/{placeId}/reports")
	public ResponseEntity<ApiResponse<List<ResponseReportDTO>>> 
						getReports(@PathVariable(required = true) long placeId) {
		
		List<ResponseReportDTO> reports = reportService.getReports(placeId);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(200, "신고 목록 조회에 성공했습니다.", reports));
	}

	// 새로운 신고 등록하기
	@PostMapping("/places/{placeId}/reports")
	public ResponseEntity<ApiResponse<Void>>  reportEnroll(
			@PathVariable(required = true) long placeId,
			@RequestBody RequestReportDTO requestReportDTO,
			 @AuthenticationPrincipal PrincipalDetail principalDetail) {
		
		// 신고 등록
		if(principalDetail == null) throw new UnauthorizedAccessException("비로그인 입니다.");
		
		Member member = principalDetail.getMember();

		reportService.reportEnroll(member, placeId, requestReportDTO);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(200, "신고 등록에 성공했습니다.", null));
	}

}
