package com.cos.blog.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status; //응답 코드
    private String message; // 메시지
    private T data; // 데이터 
}