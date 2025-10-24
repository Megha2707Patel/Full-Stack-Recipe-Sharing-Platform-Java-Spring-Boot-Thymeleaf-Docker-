# ---- Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

# ---- Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Let Spring Boot read Vercel’s provided $PORT
ENV PORT=8080
EXPOSE 8080

# If you want a bit faster startup:
ENV JAVA_OPTS="-XX:+UseZGC -XX:MaxRAMPercentage=75"

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
