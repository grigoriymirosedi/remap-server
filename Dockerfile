FROM gradle:8-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle clean buildFatJar --no-daemon --stacktrace --info

FROM eclipse-temurin:17-jre 

EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/remap-server.jar

ENTRYPOINT ["java","-jar","/app/remap-server.jar"]