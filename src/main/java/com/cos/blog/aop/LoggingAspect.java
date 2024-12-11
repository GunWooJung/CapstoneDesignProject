package com.cos.blog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.MemberService;

@Aspect
@Component
public class LoggingAspect {

	@Autowired
	MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	// 시큐리티 로그인 메서드 실행 전후에 적용s
	@Around("execution(* com.cos.blog.api.PlaceApiController.getPlaces(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		Object result;
		try {
			// 메서드 실행
			result = joinPoint.proceed();
		} finally {
			long executionTime = System.currentTimeMillis() - start;

			// 메서드명 및 실행시간 로그
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated()) {
				PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
				if (principalDetail != null) {
					// 인증된 사용자의 정보
					String username = principalDetail.getMember().getLoginId();
					logger.info("현재 로그인한 사용자: " + username + " , 실행 api : " + methodName + ", 실행 시간 : " + executionTime + "ms");
				}
			} else {
				logger.info("현재 로그인한 사용자: 비로그인 , 실행 api : " + methodName + ", 실행 시간 : " + executionTime + "ms");
			}
		}
		return result;
	}

}
