package com.cos.blog.dto.response;

import java.sql.Timestamp;

import com.cos.blog.entity.Comment;
import com.cos.blog.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ResponseCommentDTO {
	
	private final long id; 	//식별자	
	@NonNull
	private final long memberId;	//작성자
	@NonNull
	private final String name;	//작성자
	@NonNull
	private final String content;		//내용
	@NonNull
    private final Timestamp createdDate; // 생성 시간
	
	private final boolean mine;
	
	 //DTO로 변환
   	public static ResponseCommentDTO toResponseCommentDTO(Comment comment, Member member) {
        if(member == null)   
	   		return ResponseCommentDTO.builder()
	           		.id(comment.getId())
	           		.memberId(comment.getMember().getId())
	         		.name(comment.getMember().getName())
	           		.content(comment.getContent())
	           		.createdDate(comment.getCreatedDate())
	           		.mine(false)
	           		.build();
        else 
        	return ResponseCommentDTO.builder()
	           		.id(comment.getId())
	           		.memberId(comment.getMember().getId())
	         		.name(comment.getMember().getName())
	           		.content(comment.getContent())
	           		.createdDate(comment.getCreatedDate())
	           		.mine(comment.getMember().getId() == member.getId())
	           		.build();
   	}
}
