# JAVA 8 image
FROM openjdk:8-jdk-alpine

# Working directory for docker image
WORKDIR /code

# Folder inside alpine
COPY ./docker/AMICOServer-0.0.1-SNAPSHOT.jar app.jar
COPY ./docker/files files

# Expose PORT
EXPOSE 8080

# Start image
ENTRYPOINT ["java", "-jar", "app.jar"]
