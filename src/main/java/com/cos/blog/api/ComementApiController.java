package com.cos.blog.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.RequestCommentDTO;
import com.cos.blog.dto.ResponseCommentDTO;
import com.cos.blog.service.CommentService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ComementApiController {

	private final CommentService commentService;

	// id에 맞는 모든 댓글 목록 가져오기
	@GetMapping("places/{placeId}/comments")
	public ResponseEntity<ApiResponse<List<ResponseCommentDTO>>> 
			getComments(@PathVariable(required = true) long placeId) {

		List<ResponseCommentDTO> comments = commentService.getComments(placeId);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 목록 조회 성공했습니다.", comments));
	}

	// id에 맞는 모든 댓글 등록하기
	@PostMapping("places/{placeId}/comments")
	public ResponseEntity<ApiResponse<Void>> 
				enrollComment(@PathVariable(required = true) long placeId,
						@RequestBody RequestCommentDTO requestCommentDTO) {

		commentService.enrollComment(placeId, requestCommentDTO);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 작성이 성공했습니다.", null));
	}
	
	// Delete 요청인데 쿼리스트링에 비밀번호를 담음
	@DeleteMapping("places/{placeId}/comments/{commentId}")
	public ResponseEntity<ApiResponse<Void>> 
				deleteComment(@PathVariable(required = true) long placeId,
						@PathVariable(required = true) long commentId ,
						@RequestParam String password
						) {

		commentService.deleteComment(placeId, commentId, password);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 삭제가 성공했습니다.", null));
	}
}
