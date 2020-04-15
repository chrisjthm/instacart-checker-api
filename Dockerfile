FROM openjdk:8-jre-alpine
ADD . /instacart-checker
WORKDIR /instacart-checker
RUN mvn clean install
COPY --from=0 "/instacart-checker/target/instacart-checker-1.0-SNAPSHOT.jar" instacart-checker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "instacart-checker.jar", "server"]