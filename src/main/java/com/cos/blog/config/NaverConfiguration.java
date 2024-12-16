package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class NaverConfiguration {
    @Value("${ncp.accessKey}")
    private String accessKey;
    
    @Value("${ncp.secretKey}")
    private String secretKey;
    
    @Value("${ncp.regionName}")
    private String regionName;
    
    @Value("${ncp.endPoint}")
    private String endPoint;
    
    @Value("${ncp.bucketName}")
    private String bucketName;
    
    // application.properties에서 경로를 주입받음
    @Value("${ncp.directoryPath}")
    private String directoryPath;
}
