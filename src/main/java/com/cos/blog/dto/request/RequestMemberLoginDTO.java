package com.cos.blog.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestMemberLoginDTO {
	
	@NonNull
	private final String username;
	@NonNull
	private final String password;

}
