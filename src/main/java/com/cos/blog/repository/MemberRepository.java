	package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.entity.Member;

import lombok.NonNull;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByLoginId(String loginId);

	Member findByName(String name);

	Member findByLoginIdAndPassword(@NonNull String loginId, @NonNull String password);
	
}
