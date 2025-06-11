FROM eclipse-temurin:24-jdk as builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app

# Копируем только собранный JAR из стадии builder
COPY --from=builder /app/target/clothify-*.jar app.jar

ENV PORT=8090
EXPOSE $PORT

ENTRYPOINT ["java", "-Xmx256m", "-jar", "app.jar"]