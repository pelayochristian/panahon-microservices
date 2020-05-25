FROM openjdk:8-jdk-alpine
LABEL maintainer="pelayochristian.dev@gmail.com"
VOLUME /tmp
EXPOSE 8888
ARG JAR_FILE=/target/newsservice.jar
ADD ${JAR_FILE} newsservice.jar
ENTRYPOINT ["java", "-jar", "/newsservice.jar"]