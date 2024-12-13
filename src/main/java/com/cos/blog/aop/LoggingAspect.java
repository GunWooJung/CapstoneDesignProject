package com.cos.blog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cos.blog.config.auth.PrincipalDetail;

/*
@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	// 시큐리티 로그인 메서드 실행 전후에 적용ss
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
			logger.info("실행 api : " + methodName + ", 실행 시간 : " + executionTime + "ms");
		}
		return result;
	}
	
	/*
	@After("execution(* com.cos.blog.api.PlaceApiController.getPlaces(..))")
	public void username() {
		  // 현재 로그인한 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // getPrincipal()이 반환하는 객체가 PrincipalDetail인지 확인 후 캐스팅
            if (authentication.getPrincipal() instanceof PrincipalDetail) {
                PrincipalDetail user = (PrincipalDetail) authentication.getPrincipal();
                String username = user.getUsername();
                logger.info("현재 로그인한 사용자: " + username);
            } else {
                // 만약 Principal이 PrincipalDetail이 아니면 String 등 다른 타입이므로 그에 맞게 처리
                logger.info("로그인된 사용자 정보가 없습니다.");
            }
        }
	}
	
}
*/