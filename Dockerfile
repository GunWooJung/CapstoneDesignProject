FROM openjdk:17
ARG JAR_FILE=target/blog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 환경 변수로 포트를 80으로 설정
ENV SERVER_PORT=80

# Spring Boot 앱 실행, 포트는 80으로 설정
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${SERVER_PORT}"]
