package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceContainer {
	
	private Place place;
	
	int status;   // 0은 회색 , 1은 파란색 , 2는 초록색 , 3은 빨강
}
