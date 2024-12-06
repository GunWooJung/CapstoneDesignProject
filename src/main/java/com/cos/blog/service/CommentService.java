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
import com.cos.blog.entity.Place;
import com.cos.blog.handler.IncorrectPasswordException;
import com.cos.blog.handler.NoDataFoundException;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	
	private final PlaceRepository placeRepository;
	
	public List<ResponseCommentDTO> getComments(Long placeId) {
		
		Place place = placeRepository.findById(placeId).orElseThrow(
				() -> new NoSuchElementException(placeId+"번 화장실을 찾을 수 없습니다.") );
		
		List<Comment> comments = commentRepository.findByPlace(place);
		if(comments == null ||comments.size() == 0) throw new NoDataFoundException("데이터 목록이 없습니다.");
		
		return comments.stream()
				.sorted(Comparator.comparing(Comment::getCreatedDate).reversed()) // Timestamp 기준 최신순 정렬
				.map((comment) -> ResponseCommentDTO.toResponseCommentDTO(comment))
				.collect(Collectors.toList());
	}

	@Transactional
	public void enrollComment(Long placeId, RequestCommentDTO requestCommentDTO) {
		Place place = placeRepository.findById(placeId).orElseThrow(
				() -> new NoSuchElementException(placeId+"번 화장실을 찾을 수 없습니다.") );
		
		Comment comment = new Comment(
				requestCommentDTO.getName(),
				requestCommentDTO.getPassword(),
				requestCommentDTO.getContent(),
				place);
		commentRepository.save(comment);
	}

	@Transactional
	public void deleteComment(Long placeId, Long commentId
			, String password) {
		//placeId는 필요 없긴한데 일단 검증 추가
		Place place = placeRepository.findById(placeId).orElseThrow(
				() -> new NoSuchElementException(placeId+"번 화장실을 찾을 수 없습니다.") );	
		
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new NoSuchElementException(commentId+"번 댓글을 찾을 수 없습니다.") );	
		//비번 불일치
		if(!comment.getPassword().equals(password)) {
			throw new IncorrectPasswordException("비밀 번호가 다릅니다.");
		}
		
		commentRepository.deleteById(commentId);
	}

}
