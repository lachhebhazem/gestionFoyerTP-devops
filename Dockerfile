# Plus de Maven dans Docker → tout est déjà fait par Jenkins avant
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Le JAR existe déjà grâce à la commande mvnw dans Jenkins
COPY target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
