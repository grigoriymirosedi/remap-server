FROM gradle:7-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle dependencies --no-daemon && \
    gradle clean buildFatJar --no-daemon --stacktrace --info

FROM openjdk:11

EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/remap-server.jar

ENTRYPOINT ["java","-jar","/app/remap-server.jar"]