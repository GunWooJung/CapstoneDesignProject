package com.cos.blog.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.request.RequestMemberJoinDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.handler.DuplicatedIdException;
import com.cos.blog.handler.DuplicatedNameException;
import com.cos.blog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder BCryptPasswordEncoder;
	
	// 새로운 USER Member를 등록
	@Transactional
	public void memberJoin(RequestMemberJoinDTO requestMemberJoinDTO, MultipartFile image) {
		
		String encodedPassword = BCryptPasswordEncoder.encode(requestMemberJoinDTO.getPassword());
	
		Member member = new Member(
				requestMemberJoinDTO.getName(), 
				requestMemberJoinDTO.getLoginId(),
				encodedPassword,
				Member.Role.USER);
		
		memberRepository.save(member);
	}

	//사용중이면 true, 사용중이지 않으면 false
	public void memberIdCheck(String loginId) {
		
		Member member = memberRepository.findByLoginId(loginId);
		if(member != null)
			throw new DuplicatedIdException("사용 중인 아이디입니다.");
		
	}

	public void memberNameCheck(String name) {

		Member member = memberRepository.findByName(name);
		if(member != null)
			throw new DuplicatedNameException("사용 중인 이름입니다.");
	}

	/*
	public Member memberLogin(RequestMemberLoginDTO requestMemberLoginDTO) {
		
		Member member = memberRepository.findByLoginIdAndPassword(
				requestMemberLoginDTO.getLoginId(), 
				requestMemberLoginDTO.getPassword());
		
		if(member == null) // 로그인 실패
			return null; 
		else
			return member;	// 로그인 성공
	}
	*/
	public Member findByUserName(String userName) {
		
		Member member = memberRepository.findByLoginId(userName);
		if(member == null)
			throw new UsernameNotFoundException("없는 회원입니다.");
		
		return member;
	}
	
	   // 현재 로그인한 사용자 정보 가져오기
    public Member getLoggedInUserDetails() {
        // SecurityContext에서 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자 정보(Principal)가 존재하면 PrincipalDetail 반환
        if (authentication != null) {
        	PrincipalDetail principalDetail =
        			(PrincipalDetail) authentication.getPrincipal();  // PrincipalDetail로 반환
        	
        	if(principalDetail != null) {
        		Member member = principalDetail.getMember();
        		
        		if(member != null) {
        			return member;
        		}
        		
        	}
        }

        return null;  // 인증되지 않은 경우
    }

}
