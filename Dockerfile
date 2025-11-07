FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY . /workspace
# Use Gradle wrapper to build the fat jar
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
