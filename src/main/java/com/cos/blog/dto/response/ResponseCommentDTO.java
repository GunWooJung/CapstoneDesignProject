package com.cos.blog.dto.response;

import java.sql.Timestamp;

import com.cos.blog.entity.Comment;

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
	private final String content;		//내용
	@NonNull
    private final Timestamp createdDate; // 생성 시간
	
	 //DTO로 변환
   	public static ResponseCommentDTO toResponseCommentDTO(Comment comment) {
           return ResponseCommentDTO.builder()
           		.id(comment.getId())
           		.name(comment.getName())
           		.content(comment.getContent())
           		.createdDate(comment.getCreatedDate())
           		.build();
   	}
}
