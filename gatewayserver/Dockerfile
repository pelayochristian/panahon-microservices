FROM openjdk:8-jdk-alpine
LABEL maintainer="pelayochristian.dev@gmail.com"
VOLUME /tmp
EXPOSE 8888
ARG JAR_FILE=/target/gatewayserver.jar
ADD ${JAR_FILE} gatewayserver.jar
ENTRYPOINT ["java", "-jar", "/gatewayserver.jar"]