#
# escalade-rest : Dockerfile
#
#
# Build stage
#
FROM maven:3.5.2-jdk-8-alpine AS build
COPY src /srv/src
COPY pom.xml /srv
RUN mvn -f /srv/pom.xml clean package -DskipTests=true

#
# Package stage
#
FROM openjdk:8-jdk-alpine

RUN mkdir /TMP

COPY --from=build /srv/target/escalade-rest.jar /srv/escalade-rest.jar

WORKDIR /srv

RUN sh -c 'touch escalade-rest.jar'
EXPOSE 9191
ENTRYPOINT ["java","-jar","escalade-rest.jar"]

