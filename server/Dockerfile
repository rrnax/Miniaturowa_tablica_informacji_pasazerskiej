FROM openjdk:17-alpine
LABEL maintainer="DWRcompany"
COPY target/*.jar backend_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/backend_app.jar"]
