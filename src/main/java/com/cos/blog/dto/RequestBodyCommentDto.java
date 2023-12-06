package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBodyCommentDto {
	
	private String username;
	private String password;
	private String placeId;
	private String content;
	
}
