package com.cos.blog.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cos.blog.dto.ResponseCommentDTO;

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
	private long id; 	//식별자
	
	@NonNull
	@Column(name = "name" , updatable = false ,nullable = false)
	private String name;	//작성자
	
	@NonNull
	@Column(name = "password", nullable = false)
	private String password;		//비밀번호

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
    public Comment(String name, String password, String content, Place place) {
    	this.name = name;
    	this.password = password;
    	this.content = content;
    	this.place = place;
    }
	
    //DTO로 변환
   	public ResponseCommentDTO toCommentResponseDTO() {
           return ResponseCommentDTO.builder()
           		.id(id)
           		.name(name)
           		.password(password)
           		.content(content)
           		.createdDate(createdDate)
           		.build();
   	}
}
