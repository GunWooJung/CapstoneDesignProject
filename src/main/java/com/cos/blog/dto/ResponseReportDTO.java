package com.cos.blog.dto;

import java.sql.Timestamp;

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

}
