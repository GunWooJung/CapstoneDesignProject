# OpenJDK 11을 기반 이미지로 사용
FROM openjdk:11-jre-slim

# JAR 파일을 컨테이너 내부의 /app 디렉토리로 복사
COPY target/${JAR_FILE_NAME} /app/${JAR_FILE_NAME}

# 컨테이너가 실행될 때 JAR 파일을 실행하도록 설정
CMD ["java", "-jar", "/app/${JAR_FILE_NAME}"]
