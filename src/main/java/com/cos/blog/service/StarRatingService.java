package com.cos.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dao.StarRatingDAO;
import com.cos.blog.dto.response.StarCountAvgMapperDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.entity.StarRating;
import com.cos.blog.handler.DuplicatedEnrollException;
import com.cos.blog.repository.MemberRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.StarRatingRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StarRatingService {

	private final MemberRepository memberRepository;
	
	private final StarRatingDAO StarRatingDAO;

	private final PlaceRepository placeRepository;

	private final StarRatingRepository starRatingRepository;

	@Transactional
	public void enroll(HttpServletRequest request,
			long placeId, double score) {

		long id = (long) request.getAttribute("id");

		Member member = memberRepository.getReferenceById(id);

		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new IllegalArgumentException("id를 찾을 수 없습니다."));

		long already = starRatingRepository.countByMemberAndPlace(member, place);

		if (already >= 1)
			throw new DuplicatedEnrollException("이미 등록되었습니다.");

		StarRating starRating = new StarRating(member, place, score);

		starRatingRepository.save(starRating);

		List<StarRating> list = starRatingRepository.findByPlace(place);
		// 항상 1개 이상
		int count = (int) list.stream().count();
		double avg = list.stream() // Stream<Double>로 변환
				.mapToDouble((s) -> s.getScore()) // DoubleStream으로 변환
				.average() // OptionalDouble을 반환
				.orElse(0.0);

		place.setStarAverage(avg);
		place.setStarCount(count);
		placeRepository.save(place);
	}

	// @Retryable(value = DeadlockLoserDataAccessException.class, maxAttempts = 3,
	// backoff = @Backoff(delay = 500, multiplier = 3))
	// 격리수준 테스트
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void enrollTestO(long placeId, double score) throws Exception {

		int maxRetryCount = 5; // 최대 재시도 횟수
		int retryCount = 0;
		boolean updated = false;

		StarRatingDAO.InsertStarRatingP(score, placeId, 7); // 7번 유저로 하드코딩
		
		while (retryCount < maxRetryCount && !updated) {
			// 1. 현재 데이터 조회 (버전 정보 포함)
			long version = StarRatingDAO.getPlaceVersionO(placeId);

			// 2. 별점 계산 (평점과 개수를 갱신)
			StarCountAvgMapperDTO stars = StarRatingDAO.calStarO(placeId);

			// 3. 별점 정보 갱신 (낙관적 락 적용)
			long row = StarRatingDAO.updatePlaceStarCountAvgO(stars.getCount(), stars.getAverage(), // 평균 계산
					placeId, version // 버전 값 확인
			);

			// 4. 업데이트 성공 여부 확인
			if (row == 1) {
				updated = true;
			} else {
				retryCount++;
				if (retryCount >= maxRetryCount) {
					throw new Exception("Max retry attempts reached due to concurrent updates.");
				}
				// 충돌이 발생했으므로 재시도
				Thread.sleep(100); // 짧은 대기 시간 후 재시도
			}
		}
	}

	// @Retryable(value = DeadlockLoserDataAccessException.class, maxAttempts = 3,
	// backoff = @Backoff(delay = 500, multiplier = 3))
	// 격리수준 테스트
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void enrollTestP(long placeId, double score) throws Exception {

		StarRatingDAO.InsertStarRatingP(score, placeId, 7); // 7번 유저로 하드코딩
		StarCountAvgMapperDTO dto = StarRatingDAO.calStarP(placeId); // 계산용으로 조회
		StarRatingDAO.updatePlaceStarCountAvgP(dto.getCount(), dto.getAverage(), placeId); // place에 평점, 개수 업데이트

	}
}
