package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Opinion;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;

public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

}
