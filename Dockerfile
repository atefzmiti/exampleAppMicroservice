FROM openjdk:11
EXPOSE 8090
ADD target/dockerized-microservice-app.jar dockerized-microservice-app.jar
ENTRYPOINT ["java","-jar","dockerized-microservice-app.jar"]