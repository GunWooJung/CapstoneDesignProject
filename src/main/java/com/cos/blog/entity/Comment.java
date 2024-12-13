package com.cos.blog.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	//식별자
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY) //지연 로딩 
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@NonNull
	@Lob
	@Column(name = "content" , nullable = false)
	private String content;		//댓글내용
	
	@NonNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;		//장소
	
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Timestamp createdDate; // 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private Timestamp updatedDate; // 수정 시간
    
    // 필수 필드로 생성자
    public Comment(Member member, String content, Place place) {
    	this.member = member;
    	this.content = content;
    	this.place = place;
    }
	
}
