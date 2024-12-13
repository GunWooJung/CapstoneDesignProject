package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.entity.Comment;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPlace(Place place);
	
	long countByMemberAndPlace(Member member, Place place);

	// JPQL로 Place에 대한 쿼리 횟수 감소
	@Query("SELECT c FROM Comment c join fetch c.member WHERE c.place.id = :id")
	//@Query("SELECT c FROM Comment c WHERE c.place.id = :id")
	List<Comment> findByPlaceQuery(long id);
}
