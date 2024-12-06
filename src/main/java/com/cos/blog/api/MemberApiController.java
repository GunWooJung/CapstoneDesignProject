package com.cos.blog.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.request.RequestMemberJoinDTO;
import com.cos.blog.handler.DuplicatedIdException;
import com.cos.blog.handler.DuplicatedNameException;
import com.cos.blog.service.MemberService;
import com.cos.blog.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberApiController {

	private final MemberService memberService;
	
    // 회원 가입 시 아이디 중복 체크
	@GetMapping("/public/members/id-check")
	public ResponseEntity<ApiResponse<Void>> memberIdCheck
				(@RequestParam(required = true) String loginId) {
		
		boolean inUsed = memberService.memberIdCheck(loginId);
		
		if(inUsed == true) // 사용 불가능
			throw new DuplicatedIdException("사용 중인 아이디입니다.");
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "사용 가능한 아이디 입니다.", null));
	}
	
	 // 회원 가입 시 이름 중복 체크
	@GetMapping("/public/members/name-check")
	public ResponseEntity<ApiResponse<Void>> memberNameCheck
				(@RequestParam(required = true) String name) {
		
		boolean inUsed = memberService.memberNameCheck(name);
		
		if(inUsed == true) // 사용 불가능
			throw new DuplicatedNameException("사용 중인 이름입니다.");
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "사용 가능한 아이디 입니다.", null));
	}
		
	// 새로운 member를 추가하기 = 회원가입
	@PostMapping("/public/members/join")
	public ResponseEntity<ApiResponse<Void>> memberJoin
				(@RequestBody RequestMemberJoinDTO requestMemberJoinDTO) {
		
		boolean inNameUsed = memberService.memberNameCheck(requestMemberJoinDTO.getName());
		
		if(inNameUsed == true) // 사용 불가능
			throw new DuplicatedNameException("사용 중인 이름입니다.");
		
		boolean inIdUsed = memberService.memberIdCheck(requestMemberJoinDTO.getLoginId());
		
		if(inIdUsed == true) // 사용 불가능
			throw new DuplicatedIdException("사용 중인 아이디입니다.");
				
		memberService.memberJoin(requestMemberJoinDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "회원가입에 성공했습니다.", null));
	}
	

	// 로그인
	/*
	@PostMapping("/members/login")
	public ResponseEntity<ApiResponse<Void>> memberLogin
				(HttpServletRequest request,
						@RequestBody RequestMemberLoginDTO requestMemberLoginDTO) {
	try {	
		Member member = memberService.memberLogin(requestMemberLoginDTO);
		
		if(member == null) // 로그인 실패
			throw new LoginFailException("아이디 또는 비밀 번호 불일치");
		
		 // 로그인 성공 후 인증 객체 생성
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    
	    // 예: 사용자 권한이 'USER'일 경우
	    if(member.getRole() == Member.Role.USER)
	    	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    else if(member.getRole() == Member.Role.ADMIN)
	    	authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); 
	    
	    User userDetails = new User(requestMemberLoginDTO.getLoginId(), "", authorities);
	    
	    // 인증 토큰 생성
	    UsernamePasswordAuthenticationToken authenticationToken = 
	        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

	    // 세부 정보를 설정 (WebAuthenticationDetailsSource 사용)
	    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	    // SecurityContext에 인증 정보 설정 (토큰 없이 인증 정보만 저장)
	    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	} catch (BadCredentialsException e) {
		e.printStackTrace();
	}
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "로그인에 성공했습니다.", null));
	}
	*/
	
}
