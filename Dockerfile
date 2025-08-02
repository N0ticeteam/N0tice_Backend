FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY keystore.p12 /app/keystore.p12
EXPOSE 443
ENTRYPOINT ["java", "-jar", "/app.jar"]