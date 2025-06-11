FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/grupo19-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/config/
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar"]
