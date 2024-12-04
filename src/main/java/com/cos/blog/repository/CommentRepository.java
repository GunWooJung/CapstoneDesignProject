package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.entity.Comment;
import com.cos.blog.entity.Place;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPlace(Place place);
}
