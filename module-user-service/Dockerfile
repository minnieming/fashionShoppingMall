FROM openjdk:17-jdk-slim

WORKDIR /fashion-mall/module-user-service

COPY *SNAPSHOT.jar .

CMD java -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} *SNAPSHOT.jar