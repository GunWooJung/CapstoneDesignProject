package com.cos.blog.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cos.blog.dto.response.StarCountAvgMapperDTO;

//트랜잭션 격리 수준 테스트용 
@Mapper
public interface StarRatingDAO {

	// 마이바티스 별점 삽입
	@Insert("""
			insert into star_rating(created_date, score, place_id, member_id)
			values(now(), #{score}, #{placeId}, #{memberId}) 
			 """)
	long InsertStarRatingO(@Param("score") double score
			, @Param("placeId") long placeId
			, @Param("memberId") long memberId);

	@Select("""
			select version 
			from place where id = #{placeId}
			 """)
	long getPlaceVersionO(@Param("placeId") long placeId);
	// 마이바티스 평점, 개수 계산
	@Select("""
			select count(score) as count, avg(score) as average 
			from star_rating as star where place_id = #{placeId}
			 """)
	StarCountAvgMapperDTO calStarO(@Param("placeId") long placeId);
	
	// 마이바티스 평점, 개수 화장실 쪽에 갱신
	@Update("""
			update place set star_count = #{count}, star_average = #{average}
			, version = version + 1 
			 where id = #{placeId} and version = #{version}
			 """)
	long updatePlaceStarCountAvgO(@Param("count") int count
			, @Param("average") double average
			, @Param("placeId") long placeId
			, @Param("version") long version);
	
	// 마이바티스 별점 삽입
		@Insert("""
				insert into star_rating(created_date, score, place_id, member_id)
				values(now(), #{score}, #{placeId}, #{memberId}) 
				 """)
		long InsertStarRatingP(@Param("score") double score
				, @Param("placeId") long placeId
				, @Param("memberId") long memberId);

		// 마이바티스 평점, 개수 계산
		@Select("""
				select count(score) as count, avg(score) as average 
				from star_rating as star where place_id = #{placeId} FOR UPDATE
				 """)
		StarCountAvgMapperDTO calStarP(@Param("placeId") long placeId);
		
		// 마이바티스 평점, 개수 화장실 쪽에 갱신
		@Update("""
				update place set star_count = #{count}, star_average = #{average}
				 where id = #{placeId} 
				 """)
		long updatePlaceStarCountAvgP(@Param("count") int count
				, @Param("average") double average
				, @Param("placeId") long placeId);
	
}
