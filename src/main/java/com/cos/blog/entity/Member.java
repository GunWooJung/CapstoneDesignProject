package com.cos.blog.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 	//식별자

	@NonNull
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;  // 이름
    
	@NonNull
    @Column(name = "login_id", nullable = false, unique = true, length = 30)
    private String loginId;  // 아이디

	@NonNull
    @Column(name = "password", nullable = false, length = 300)
    private String password;  // 비밀번호

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Timestamp createdDate; // 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private Timestamp updatedDate; // 수정 시간
 
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;  // 역할 (ENUM)

    public enum Role {
        USER, ADMIN
    }
    
    public Member(String name, String loginId, String password, Role role){
    	this.name = name;
    	this.loginId = loginId;
    	this.password = password;
    	this.role = role;
    }
    
}