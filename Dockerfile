FROM gradle:latest AS BUILD_STAGE

WORKDIR /tmp

COPY gradle gradle
COPY build.gradle.kts gradle.properties settings.gradle.kts gradlew ./
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon buildFatJar

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=BUILD_STAGE /tmp/build/libs/*-all.jar /app/ktor-server.jar

COPY .env .env

EXPOSE 8080

ENTRYPOINT ["java", "-Xlog:gc+init", "-XX:+PrintCommandLineFlags", "-jar", "/app/ktor-server.jar"]
