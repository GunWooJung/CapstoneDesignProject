package com.cos.blog.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "star_rating")
public class StarRating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	//식별자
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY) //지연 로딩 
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;
	
	@Column(name = "score" , updatable = false)
	private double score; // 1~5 별점
	
	@CreationTimestamp // 별점 작성 시간 자동 생성
	@Column(name = "created_date", nullable = false, updatable = false)
	private Timestamp createdDate;

	// 생성자: 필수 값 강제
	public StarRating(Place place, double score) {
		this.place = place;
		this.score = score;
	}
}
