FROM openjdk:21-slim

COPY target/proposta-app.jar proposta.jar
ENTRYPOINT ["java","-jar","proposta.jar"]