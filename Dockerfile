# Build: compila o projeto Java via Maven.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn -B -ntp -DskipTests package

# Runtime: só inclui a JVM e o JAR, sem o Maven.
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/target/springboot-swagger-h2-vercel-0.0.1-SNAPSHOT.jar app.jar

# Vercel espera HTTP na porta 80 por padrão.
# Se a plataforma definir PORT, Spring Boot usará essa variável.
ENV PORT=80
EXPOSE 80

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70.0", "-jar", "/app/app.jar"]
