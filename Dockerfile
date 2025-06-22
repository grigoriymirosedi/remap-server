FROM gradle:7-jdk11 AS build

COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts gradle.properties /home/gradle/src/
COPY --chown=gradle:gradle gradle /home/gradle/src/gradle
RUN gradle dependencies --no-daemon

COPY --chown=gradle:gradle . /home/gradle/src

RUN gradle clean buildFatJar --no-daemon --stacktrace --info --scan

FROM openjdk:11

EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/remap-server.jar

ENTRYPOINT ["java","-jar","/app/remap-server.jar"]