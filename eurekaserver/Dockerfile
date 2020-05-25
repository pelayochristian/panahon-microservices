FROM openjdk:8-jdk-alpine
LABEL maintainer="pelayochristian.dev@gmail.com"
VOLUME /tmp
EXPOSE 8888
ARG JAR_FILE=/target/eurekaserver.jar
ADD ${JAR_FILE} eurekaserver.jar
ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]