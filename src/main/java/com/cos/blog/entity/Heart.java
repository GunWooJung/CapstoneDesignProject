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
@Table(name = "heart")
public class Heart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	//식별자
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY) //지연 로딩 
	@JoinColumn(name = "report_id", nullable = false)
	private Report report;
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY) //지연 로딩 
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	@CreationTimestamp // 별점 작성 시간 자동 생성
	@Column(name = "created_date", nullable = false, updatable = false)
	private Timestamp createdDate;

	// 생성자: 필수 값 강제
	public Heart(Report report, Member member) {
		this.report = report;
		this.member = member;
	}
}
