FROM gradle:8.2.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:17-jdk-slim

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/config-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/config-server-0.0.1-SNAPSHOT.jar"]