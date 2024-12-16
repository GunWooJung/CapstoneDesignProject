package com.cos.blog.storage;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {

	// 생성된 uuid 파일 이름 반환
	public String uploadFile(MultipartFile img);

	public void deleteFile(String imageFileName);

}