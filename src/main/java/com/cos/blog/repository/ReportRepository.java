package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	List<Report> findByPlace(Place place);
}
