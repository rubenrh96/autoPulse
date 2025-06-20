FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . /app
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 10000

CMD ["java", "-jar", "target/springItv-0.0.1-SNAPSHOT.jar"]
