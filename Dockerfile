FROM maven:3-jdk-8
ADD . /instacart-checker
WORKDIR /instacart-checker
RUN mvn clean install
FROM openjdk:8-jre-alpine
COPY --from=0 "/instacart-checker/target/instacart-checker-1.0-SNAPSHOT.jar" instacart-checker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "instacart-checker.jar", "server"]