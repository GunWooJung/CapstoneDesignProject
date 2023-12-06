package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.model.StarRating;

public interface StarRatingRepository  extends JpaRepository<StarRating, Integer>  {
	List<StarRating> findByPlace(Place place);

}

