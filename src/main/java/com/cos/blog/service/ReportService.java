package com.cos.blog.service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.request.RequestReportDTO;
import com.cos.blog.dto.response.ResponseReportDTO;
import com.cos.blog.entity.Heart;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;
import com.cos.blog.handler.DuplicatedEnrollException;
import com.cos.blog.handler.NoDataFoundException;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.repository.HeartRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	
	private final HeartRepository heartRepository;
	
	private final PlaceRepository placeRepository;
	
	public List<ResponseReportDTO> getReports(long placeId) {
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
		List<Report> reports = reportRepository.findByPlace(place);
		
		if(reports == null || reports.size() == 0) throw new NoDataFoundException("데이터 목록이 없습니다.");
		
		return reports.stream()
				.sorted(Comparator.comparing(Report::getCreatedDate).reversed())
				.map(report -> ResponseReportDTO.toResponseReportDTO(report))
				.filter(report -> !report.getType().equals("userOpinion"))
				// 개발자에게 의견은 미노출
				.collect(Collectors.toList());
	}

	@Transactional
	public void reportEnroll(Member member, long placeId, RequestReportDTO requestReportDTO) {
		
		if(member == null)
			throw new UnauthorizedAccessException("사용자를 찾을 수 없습니다.");
		
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리

		long already = heartRepository.alreadyReportCheck(place, member, requestReportDTO.getType(),
				requestReportDTO.getContent());
		// Global 예외로 처리
				
		if(already >= 1) 
			throw new DuplicatedEnrollException("이미 등록되었습니다.");
		
		Report findReport = reportRepository.findByPlaceAndTypeAndContent(place
				, requestReportDTO.getType(),requestReportDTO.getContent());
		
		if(findReport != null) {
			
			findReport.heartPlusOne(); // 하트 1증가
			
			reportRepository.save(findReport);
			
			Heart heart = new Heart(findReport, member);
			
			heartRepository.save(heart);
			
		}else {
			// 인스턴스 생성
			Report report = new Report(requestReportDTO.getType(),
					requestReportDTO.getContent(), place);
			
			reportRepository.save(report);
			
			Heart heart = new Heart(report, member);
			
			heartRepository.save(heart);
		}
		
	}

}
