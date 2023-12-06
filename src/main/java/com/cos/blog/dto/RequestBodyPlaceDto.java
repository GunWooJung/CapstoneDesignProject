package com.cos.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBodyPlaceDto {
	
	private String lng ;
	
	private String lat ;
	
	private String changing_table_man;
	
	private String changing_table_woman;
	
	private String disabled_person;
	
	private String emergency_bell_disabled;
	
	private String emergency_bell_man;
	
	private String emergency_bell_woman;

	private String leftValue;

	private String rightValue;
	
	private String rated;
	
	private String not_rated;
}
