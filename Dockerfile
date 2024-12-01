# Build stage
FROM eclipse-temurin:22-jdk-jammy AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Run stage
FROM eclipse-temurin:22-jdk-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user
RUN groupadd -r oba && useradd -r -g oba oba
USER oba:oba

EXPOSE 8080
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]