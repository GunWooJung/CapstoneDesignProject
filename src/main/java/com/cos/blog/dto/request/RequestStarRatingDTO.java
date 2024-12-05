package com.cos.blog.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestStarRatingDTO {
	
	private final long placeId;
	@DecimalMin(value = "1.0")
	@DecimalMax(value = "5.0")
	private final double score;

}
