package com.cos.blog.dto.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestReportDTO {

	private final long placeId;
	@NonNull
	private final String type;
	@NonNull
	private final String content;
}
