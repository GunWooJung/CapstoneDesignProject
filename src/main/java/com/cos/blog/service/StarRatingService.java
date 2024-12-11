package com.cos.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.StarRating;
import com.cos.blog.handler.DuplicatedEnrollException;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.StarRatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StarRatingService {
	
	private final PlaceRepository placeRepository;

	private final StarRatingRepository starRatingRepository;
	
	@Transactional
	public void enroll(Member member, long placeId, double score) {
		
		Place place = placeRepository.findById(placeId)
				.orElseThrow( () -> new IllegalArgumentException("id를 찾을 수 없습니다.") );
	
		if(member == null)
			throw new UnauthorizedAccessException("사용자를 찾을 수 없습니다.");
	
		long already = starRatingRepository.countByMemberAndPlace(member, place);
		
		if(already >= 1) 
			throw new DuplicatedEnrollException("이미 등록되었습니다.");
		
		StarRating starRating = new StarRating(member, place, score);
		
		starRatingRepository.save(starRating);
		List<StarRating> list = starRatingRepository.findByPlace(place);
		// 항상 1개 이상
		int count = (int) list.stream().count();
		double avg = list.stream()           // Stream<Double>로 변환
                 .mapToDouble((s) -> s.getScore()) // DoubleStream으로 변환
                 .average()                      // OptionalDouble을 반환
                 .orElse(0.0);    
		
		place.setStarAverage(avg);
		place.setStarCount(count);
		placeRepository.save(place);
	}

}
