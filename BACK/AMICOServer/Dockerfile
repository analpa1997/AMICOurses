FROM maven:3.5-jdk-8-alpine AS build

WORKDIR /code

COPY pom.xml /code/pom.xml
#RUN ["mvn", "dependency:resolve"]

# Adding source, compile and package into a fat jar
COPY ["src", "/code/src"]
RUN ["mvn", "package"]

FROM openjdk:8-jdk-alpine

COPY ["files", "/code/target/files"]
COPY --from=build /code/target/AMICOServer-0.0.1-SNAPSHOT.jar /

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/AMICOServer-0.0.1-SNAPSHOT.jar"]

