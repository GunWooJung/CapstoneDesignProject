package com.cos.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cos.blog.entity.Heart;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.Report;

public interface HeartRepository  extends JpaRepository<Heart, Long>  {

	List<Heart> findByReport(Report report);

	@Query("SELECT COUNT(*) FROM Heart r " +
		       "WHERE r.member = :member AND r.report.place = :place " +
		       "and r.report.type = :type and r.report.content = :content")
	//신고 등록시 이미 제출 여부
	long alreadyReportCheck(@Param("place")Place place, @Param("member") Member member
			,@Param("type") String type, @Param("content") String content);
	
	//하트 클릭 시 이미 등록 여부
	long countByMemberAndReport(Member member, Report report);
	
}

