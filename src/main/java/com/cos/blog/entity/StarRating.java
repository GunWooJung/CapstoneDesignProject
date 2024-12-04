package com.cos.blog.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
	private long id; 	//식별자
	
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
