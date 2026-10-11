# ==========================================
# Stage 1: Build Stage with Maven and Java 17
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven project descriptor and source files
COPY pom.xml .
COPY src ./src

# Package the application into an executable Spring Boot JAR
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Runtime Stage with JRE 17
# ==========================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Render passes PORT dynamically (with 10000 as fallback)
ENV PORT=10000
ENV SPRING_PROFILES_ACTIVE=prod

# Copy the executable JAR from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Document container port
EXPOSE 10000

# Execute Spring Boot with Render's PORT variable
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar app.jar"]
