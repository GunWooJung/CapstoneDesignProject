package com.cos.blog.handler;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cos.blog.util.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	// 존재하지 않는 페이지 404
	@ExceptionHandler(InvalidPageException.class)
	public void InvalidPageException
	(InvalidPageException e, HttpServletResponse response) throws IOException{
		//e.printStackTrace();
        response.sendRedirect("/404"); // 여기서 "/404"는 404 에러 페이지의 URL입니다.	}
	}
	
	//principal이 null
   @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedAccessException(UnauthorizedAccessException e) {
	   //e.printStackTrace();
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(401, "권한이 없습니다.", null));	
   }
	
	// 내용이 없으면 200 예외처리
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNull(NoDataFoundException e) {
		//e.printStackTrace();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "조회에 성공했지만 내용이 없습니다.", null));	
    }
	
	// 자원을 찾을 수 없는 경우 404 에러
	@ExceptionHandler({IllegalArgumentException.class,NoSuchElementException.class})
    public ResponseEntity<ApiResponse<Void>> handleNotFound(Exception e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, "조회에 성공했지만, 목록이 없습니다.", null));	
    }
	
	// 인증이 실패한 경우 401 에러
	@ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnAuthorized(IncorrectPasswordException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(401, "권한이 없습니다.", null));	
    }
	
	// 주소 형식이 잘못된 경우 400 에러
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, 
		MissingServletRequestParameterException.class,
		IllegalStateException.class,
	    DataIntegrityViolationException.class,
	    NullPointerException.class})
	public ResponseEntity<ApiResponse<Void>> handleMethodArguementType
    (Exception e) {
		e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "잘못된 요청입니다.", null));	
    }
	
	// 아이디 중복
	@ExceptionHandler(DuplicatedIdException.class)
	public ResponseEntity<ApiResponse<Void>> DuplicatedIdException
    (DuplicatedIdException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(409, "중복된 아이디 입니다.", null));	
    }
	
	
	
	// 아이디가 없음
	@ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> UsernameNotFoundException
    (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(401, "없는 회원입니다.", null));	
    }
	// 이름 중복
	@ExceptionHandler(DuplicatedNameException.class)
	public ResponseEntity<ApiResponse<Void>> DuplicatedNameException
    (DuplicatedNameException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(409, "중복된 이름 입니다.", null));	
    }
	
	// 이미 회원이 등록한 데이터가 존재함
	@ExceptionHandler(DuplicatedEnrollException.class)
	public ResponseEntity<ApiResponse<Void>> DuplicatedEnrollException
    (DuplicatedEnrollException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(409, "이미 등록하였습니다.", null));	
    }
	
	// 로그인 실패
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity<ApiResponse<Void>> LoginFailException
    (LoginFailException e) {
		//e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(401, "아이디 또는 비밀번호 불일치", null));	
    }
		
	// 내부 서버 오류 500 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
    	e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "서버 오류입니다.", null));	
    }
}
