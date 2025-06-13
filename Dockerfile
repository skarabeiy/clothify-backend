# Dockerfile for Render hosting
FROM maven:3.9-eclipse-temurin-24 as builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app

# Копируем только собранный JAR из стадии builder
COPY --from=builder /app/target/clothify-*.jar app.jar

ENV PORT=8090
EXPOSE $PORT

ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]