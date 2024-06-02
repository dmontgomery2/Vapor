FROM openjdk:17.0.2-jdk AS build

WORKDIR /app

# RUN apt update && apt install dos2unix

COPY . .

RUN ./mvnw clean package spring-boot:repackage

FROM openjdk:17.0.2

WORKDIR /app 

COPY --from=build /app/target/*.jar .

ENTRYPOINT ["java", "-jar", "Vapor-0.0.1-SNAPSHOT.jar"]

