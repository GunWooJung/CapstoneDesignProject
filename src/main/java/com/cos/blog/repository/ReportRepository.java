package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	List<Report> findByPlace(Place place);
	List<Report> findAllByOrderByCreateDateAsc();
}
