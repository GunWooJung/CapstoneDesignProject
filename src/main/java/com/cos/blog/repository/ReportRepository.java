package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;

import lombok.NonNull;

public interface ReportRepository extends JpaRepository<Report, Long> {

	List<Report> findByPlace(Place place);

	Report findByPlaceAndTypeAndContent(Place place, @NonNull String type, @NonNull String content);	
}
