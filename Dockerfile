FROM maven:3-jdk-11 as builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:11-slim

COPY --from=builder /app/target/ees-site.jar /app.jar

CMD ["java", "-jar", "/app.jar", "--server.port=${PORT}"]