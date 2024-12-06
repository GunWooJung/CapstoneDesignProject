package com.cos.blog.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.request.RequestMemberJoinDTO;
import com.cos.blog.entity.Member;
import com.cos.blog.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final BCryptPasswordEncoder BCryptPasswordEncoder;
	
	// 새로운 USER Member를 등록
	@Transactional
	public void memberJoin(RequestMemberJoinDTO requestMemberJoinDTO) {
		
		String encodedPassword = BCryptPasswordEncoder.encode(requestMemberJoinDTO.getPassword());
	
		Member member = new Member(
				requestMemberJoinDTO.getName(), 
				requestMemberJoinDTO.getLoginId(),
				encodedPassword,
				Member.Role.USER);
		
		memberRepository.save(member);
	}

	//사용중이면 true, 사용중이지 않으면 false
	public boolean memberIdCheck(String loginId) {
		
		Member member = memberRepository.findByLoginId(loginId);
		if(member == null)
			return false; // 사용 가능
		else
			return true;  //사용 불가
	}

	public boolean memberNameCheck(String name) {
		
		Member member = memberRepository.findByName(name);
		if(member == null)
			return false; // 사용 가능
		else
			return true;  //사용 불가
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
	
	

}
