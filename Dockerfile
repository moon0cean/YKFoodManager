FROM openjdk:17-jdk-alpine
MAINTAINER jonatanSoto
COPY target/foodManager-1.0-0-alpha.jar food-manager-1.0.0-alpha.jar
ENTRYPOINT ["java","-jar","/food-manager-1.0.0-alpha.jar"]