package com.cos.blog.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cos.blog.entity.Member;
import com.cos.blog.entity.SecurityDetails;
import com.cos.blog.handler.NoDataFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final MemberService memberService;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
    	Member member = memberService.findByUserName(userName);

    	if(member == null) {
    		throw new NoDataFoundException("해당 사용자가 없습니다.");
    	}
        return new SecurityDetails(member);
    }
}