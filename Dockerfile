FROM gradle:8-jdk17 AS build

WORKDIR /home/gradle
COPY . .

ENV GRADLE_USER_HOME /home/gradle/.gradle

RUN chown -R gradle:gradle /home/gradle

USER gradle

RUN gradle clean build --no-daemon --stacktrace --info --warning-mode=all --scan

FROM eclipse-temurin:17-jre

EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/build/libs/*.jar /app/remap-server.jar

ENTRYPOINT ["java","-jar","/app/remap-server.jar"]