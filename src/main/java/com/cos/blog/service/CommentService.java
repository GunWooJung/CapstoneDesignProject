package com.cos.blog.service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.request.RequestCommentDTO;
import com.cos.blog.dto.response.ResponseCommentDTO;
import com.cos.blog.entity.Comment;
import com.cos.blog.entity.Member;
import com.cos.blog.entity.Place;
import com.cos.blog.handler.DuplicatedEnrollException;
import com.cos.blog.handler.NoDataFoundException;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.MemberRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.util.Status;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final MemberRepository memberRepository;
	
	private final CommentRepository commentRepository;

	private final PlaceRepository placeRepository;

	@Transactional(readOnly = true)
	public List<ResponseCommentDTO> getComments(
			HttpServletRequest request,
			long placeId) {

		long id = (long) request.getAttribute("id");
		
		Member member = memberRepository.getReferenceById(id);
		
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));

		List<Comment> comments = commentRepository.findByPlaceAndStatus(place, Status.ACTIVE);
		
		if (comments == null || comments.size() == 0)
			throw new NoDataFoundException("데이터 목록이 없습니다.");

		return comments.stream().sorted(Comparator.comparing(Comment::getCreatedDate).reversed()) // Timestamp 기준 최신순 정렬
				.map((comment) -> ResponseCommentDTO.toResponseCommentDTO(comment, member))
				.collect(Collectors.toList());
	}

	@Transactional
	public void enrollComment(HttpServletRequest request,
			long placeId, RequestCommentDTO requestCommentDTO) {
		
		long id = (long) request.getAttribute("id");
		
		Member member = memberRepository.getReferenceById(id);
		
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));

		long already = commentRepository.countByMemberAndPlaceAndStatus(member, place, Status.ACTIVE);

		if (already >= 1)
			throw new DuplicatedEnrollException("이미 등록되었습니다.");
		//새로운 댓글 생성
		Comment comment = new Comment(member, requestCommentDTO.getContent(), place);
		comment.setStatus(Status.ACTIVE);
		commentRepository.save(comment);

		List<Comment> comments = commentRepository.findByPlaceAndStatus(place, Status.ACTIVE);
		// 항상 1개 이상
		int count = (int) comments.stream().count();

		place.setCommentCount(count);
		placeRepository.save(place);
	}

	@Transactional
	public void deleteComment(HttpServletRequest request,
			long placeId, 
			long commentId,
			long memberId) {

		long id = (long) request.getAttribute("id");
		
		Member member = memberRepository.getReferenceById(id);
		
		// placeId는 필요 없긴한데 일단 검증 추가
		Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new NoSuchElementException(placeId + "번 화장실을 찾을 수 없습니다."));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new NoSuchElementException(commentId + "번 댓글을 찾을 수 없습니다."));

		// 자신이 쓴 건지 비교
		if (member.getId() != memberId) {
			throw new UnauthorizedAccessException("권한이 없습니다.");
		}
		comment.setStatus(Status.DELETED);
		commentRepository.save(comment);

		List<Comment> comments = commentRepository.findByPlaceAndStatus(place, Status.ACTIVE);
		// 항상 1개 이상
		int count = (int) comments.stream().count();

		place.setCommentCount(count);
		placeRepository.save(place);
	}

	/* 테스트용
	public List<ResponseCommentDTO> getCommentsTest(long id) {

		List<Comment> comments = commentRepository.findByPlaceQuery(id);
		if (comments == null || comments.size() == 0)
			throw new NoDataFoundException("데이터 목록이 없습니다.");

		return comments.stream().sorted(Comparator.comparing(Comment::getCreatedDate).reversed()) // Timestamp 기준 최신순 정렬
				.map((comment) -> ResponseCommentDTO.toResponseCommentDTO(comment, null)).collect(Collectors.toList());
	}
	*/
}
