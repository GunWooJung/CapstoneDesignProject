package com.cos.blog.dto.response;

import java.sql.Timestamp;

import com.cos.blog.entity.Report;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ResponseReportDTO {

	private final long id; // 식별자

	@NonNull
	private final String type;	// 신고 유형
	@NonNull	
	private final String content;	//신고 내용
	
	private final int heart;
	@NonNull
	private final Timestamp createdDate; // 생성 시간

    
    //DTO로 변환
   	public static ResponseReportDTO toResponseReportDTO(Report report) {
           return ResponseReportDTO.builder()
           		.id(report.getId())
           		.type(report.getType())
           		.content(report.getContent())
           		.createdDate(report.getCreatedDate())
           		.heart(report.getHeart())
           		.build();
   	}
}
