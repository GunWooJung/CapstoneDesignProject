package com.cos.blog.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestMemberJoinDTO {
	
	@NonNull
	private final String name;
	@NonNull
	private final String loginId;
	@NonNull
	private final String password;
	
}
