package com.cos.blog.service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.request.RequestReportDTO;
import com.cos.blog.dto.response.ResponseReportDTO;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;
import com.cos.blog.handler.NoDataFoundException;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

	private final ReportRepository reportRepository;
	
	private final PlaceRepository placeRepository;
	
	public List<ResponseReportDTO> getReports(Long placeId) {
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
	public void reportEnroll(Long placeId, RequestReportDTO requestReportDTO) {
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
		
		// 인스턴스 생성
		Report report = new Report(requestReportDTO.getType(),
				requestReportDTO.getContent(), place);
		
		reportRepository.save(report);
	}

	@Transactional
	public void reportHeartClick(long placeId, long reportId) {
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
		
		Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new NoSuchElementException(reportId + "번 신고를 찾을 수 없습니다."));
		// Global 예외로 처리
		report.clickHeart();
		reportRepository.save(report);
	}

}
