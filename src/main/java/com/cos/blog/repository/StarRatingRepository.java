package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.entity.Place;
import com.cos.blog.entity.StarRating;

public interface StarRatingRepository  extends JpaRepository<StarRating, Long>  {
	
	List<StarRating> findByPlace(Place place);

}

