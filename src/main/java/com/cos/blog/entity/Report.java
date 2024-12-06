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
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	//식별자
		
	@NonNull
	@Column(name = "type", updatable = false, nullable = false)
	private String type;	//신고유형
	
	@NonNull
	@Column(name = "content", updatable = false, nullable = false)
	private String content;	//신고 내용
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;	//장소
	
	@Setter
	@Column(name = "heart")
	private int heart;
	
	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false)
	private Timestamp createdDate; // 생성 시간

    // 필수 필드로 생성자
    public Report(String type, String content, Place place) {
    	this.type = type;
    	this.content = content;
    	this.place = place;
    	this.heart = 1;
    }
    
    public void heartPlusOne() {
    	this.heart = this.heart + 1;
    }

}
