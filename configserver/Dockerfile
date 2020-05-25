FROM openjdk:8-jdk-alpine
LABEL maintainer="pelayochristian.dev@gmail.com"
VOLUME /tmp
EXPOSE 8888
ARG JAR_FILE=/target/configserver.jar
ADD ${JAR_FILE} configserver.jar
ENTRYPOINT ["java", "-jar", "/configserver.jar"]