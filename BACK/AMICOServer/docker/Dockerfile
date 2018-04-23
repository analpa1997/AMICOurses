FROM openjdk:8-jdk-alpine

WORKDIR /usr/src

COPY AMICOServer-0.0.1-SNAPSHOT.jar /usr/src
COPY ./files /usr/src/files

CMD ["java", "-jar", "AMICOServer-0.0.1-SNAPSHOT.jar"]