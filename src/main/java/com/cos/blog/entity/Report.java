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

import com.cos.blog.dto.ResponseReportDTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 	//식별자
	
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
    	this.heart = 1; //최초 신고 초기값은 1
    }
    
    //DTO로 변환
   	public ResponseReportDTO toReportResponseDTO() {
           return ResponseReportDTO.builder()
           		.id(id)
           		.type(type)
           		.content(content)
           		.createdDate(createdDate)
           		.heart(heart)
           		.build();
   	}
   	
   	public void clickHeart() {
   		this.heart += 1;
   	}
}
