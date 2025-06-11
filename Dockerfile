FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/grupo19-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8099
ENTRYPOINT ["java", "-jar", "app.jar"]
