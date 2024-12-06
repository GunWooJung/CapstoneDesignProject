package com.cos.blog.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.entity.Heart;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;
import com.cos.blog.handler.DuplicatedEnrollException;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.repository.HeartRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartService {

	private final ReportRepository reportRepository;
	
	private final PlaceRepository placeRepository;

	private final HeartRepository heartRepository;
	
	@Transactional
	public void clickHeart(long placeId, long reportId, Member member) {
		
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));
		// Global 예외로 처리
		
		Report report = reportRepository.findById(reportId)
				.orElseThrow(() -> new NoSuchElementException(reportId + "번 신고를 찾을 수 없습니다."));
		// Global 예외로 처리
		
		if(member == null)
			throw new UnauthorizedAccessException("사용자를 찾을 수 없습니다.");
		
		long already = heartRepository.countByMemberAndReport(member, report);
		// Global 예외로 처리
				
		if(already >= 1) 
			throw new DuplicatedEnrollException("이미 등록되었습니다.");
	
		Heart heart = new Heart(report, member);
		
		heartRepository.save(heart);
		
		List<Heart> list = heartRepository.findByReport(report);
		// 항상 1개 이상
		int count = (int) list.stream().count();
		report.setHeart(count);
		reportRepository.save(report);

	}


}
