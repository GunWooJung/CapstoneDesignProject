package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.entity.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

	// 검색한 장소가 현재 위치의 1km이내 쿼리
	@Query("SELECT p FROM Place p WHERE LOWER(REPLACE(p.name, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(:keyword, ' ', ''), '%'))")
	List<Place> getPlacesByKeyword(String keyword);

	@Query("select p from Place p where "+
			"p.lat between :latFrom and :latTo "+
			"and p.lng between :lngFrom and :lngTo")
	List<Place> getPlaces(double latFrom, double latTo,
			double lngFrom, double lngTo);
	
}
