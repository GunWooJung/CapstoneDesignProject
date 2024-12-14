package com.cos.blog.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.request.RequestCommentDTO;
import com.cos.blog.dto.response.ResponseCommentDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.handler.UnauthorizedAccessException;
import com.cos.blog.service.CommentService;
import com.cos.blog.service.MemberService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ComementApiController {

	private final MemberService memberService;

	private final CommentService commentService;

	// id에 맞는 모든 댓글 목록 가져오기
	@GetMapping("places/{id}/comments")
	public ResponseEntity<ApiResponse<List<ResponseCommentDTO>>> getComments(@PathVariable(required = true) long id) {
		
		//로그인 정보 꺼내기
		Member member = memberService.getLoggedInUserDetails();

		List<ResponseCommentDTO> comments = commentService.getComments(id, member);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 목록 조회 성공했습니다.", comments));
	}

	// id에 맞는 모든 댓글 등록하기
	@PostMapping("places/{id}/comments")
	public ResponseEntity<ApiResponse<Void>> enrollComment(@PathVariable(required = true) long id,
			@RequestBody RequestCommentDTO requestCommentDTO) {

		//로그인 정보 꺼내기
		Member member = memberService.getLoggedInUserDetails();

		commentService.enrollComment(member, id, requestCommentDTO);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 작성이 성공했습니다.", null));
	}

	// Delete 요청
	@DeleteMapping("places/{placeId}/comments/{commentId}")
	public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable(required = true) long placeId,
			@PathVariable(required = true) long commentId, @RequestParam long memberId) {

		//로그인 정보 꺼내기
		Member member = memberService.getLoggedInUserDetails();

		// 작성자랑 principal 아이디 비교
		commentService.deleteComment(placeId, commentId, member, memberId);

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "댓글 삭제가 성공했습니다.", null));
	}

	/*
	 * // 쿼리 분석 테스트 // id에 맞는 모든 댓글 목록 가져오기
	 * 
	 * @GetMapping("places/{id}/comments/test") public
	 * ResponseEntity<ApiResponse<List<ResponseCommentDTO>>>
	 * getCommentsTest(@PathVariable(required = true) long id) {
	 * 
	 * List<ResponseCommentDTO> comments = commentService.getCommentsTest(id);
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200,
	 * "댓글 목록 조회 성공했습니다.", comments)); }
	 */
}
