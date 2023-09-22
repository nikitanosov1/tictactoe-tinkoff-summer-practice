FROM gradle:8.1.1 AS BUILD

WORKDIR /opt/app

COPY . .

RUN gradle :mediator:bootJar

FROM openjdk:17.0.1-jdk-slim

ARG JAR_FILE=/opt/app/mediator/build/libs/mediator-*.jar

WORKDIR /opt/app

COPY --from=BUILD ${JAR_FILE} tictactoebot.jar

ENTRYPOINT ["java","-jar","tictactoebot.jar"]