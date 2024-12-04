package com.cos.blog.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ResponseCommentDTO {
	
	private final long id; 	//식별자	
	@NonNull
	private final String name;	//작성자
	@NonNull
	private final String password;	//비밀번호
	@NonNull
	private final String content;		//내용
	@NonNull
    private final Timestamp createdDate; // 생성 시간
	
}
