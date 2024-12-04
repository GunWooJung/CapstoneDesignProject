package com.cos.blog.handler;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cos.blog.util.ApiResponse;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	
	// 내용이 없으면 예외처리
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleNull(NullPointerException e) {
		//e.printStackTrace();
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "조회에 성공했지만 내용이 없습니다.", null));	
    }
	
	// 자원을 찾을 수 없는 경우 404 에러
	@ExceptionHandler({IllegalArgumentException.class,NoSuchElementException.class})
    public ResponseEntity<ApiResponse<Void>> handleNotFound(Exception e) {
		e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(200, "조회에 성공했지만, 목록이 없습니다.", null));	
    }
	
	// 인증이 실패한 경우 401 에러
	@ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnAuthorized(IncorrectPasswordException e) {
		e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(401, "권한이 없습니다.", null));	
    }
	
	// 주소 형식이 잘못된 경우 400 에러
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, 
		MissingServletRequestParameterException.class,
		IllegalStateException.class})
	public ResponseEntity<ApiResponse<Void>> handleMethodArguementType
    (Exception e) {
		e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, "잘못된 요청입니다.", null));	
    }
	
	// 내부 서버 오류 500 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception e) {
    	e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(500, "서버 오류입니다.", null));	
    }
}
