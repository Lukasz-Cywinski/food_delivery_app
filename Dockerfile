FROM eclipse-temurin:latest
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
