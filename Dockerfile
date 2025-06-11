FROM eclipse-temurin:24-jdk
WORKDIR /app
COPY target/clothify-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8090
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]