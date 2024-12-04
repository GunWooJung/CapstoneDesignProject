package com.cos.blog.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestPlaceDTO {
	//위도
	@DecimalMin(value = "-90.0")
	@DecimalMax(value = "90.0")
	private final double lat;
	//경도
	@DecimalMin(value = "-180.0")
	@DecimalMax(value = "180.0")
	private final double lng;
	//장애인
	private final boolean disabledPerson;
	//기저귀 남자
	private final boolean changingTableMan;
	//기저귀 여자
	private final boolean changingTableWoman;
	//비상벨 남자
	private final boolean emergencyBellMan;
	//비상벨 여자
	private final boolean emergencyBellWoman;
	//비상벨 장애인
	private final boolean emergencyBellDisabled;
	//최소 평점
	@DecimalMin(value = "1.0")
	@DecimalMax(value = "5.0")
	private final double leftValue;
	//최대 평점
	@DecimalMin(value = "1.0")
	@DecimalMax(value = "5.0")
	private final double rightValue;
	//평가
	private final boolean rated;
	//미평가
	private final boolean notRated;
	
}
