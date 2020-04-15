FROM openjdk:8-jre-alpine
WORKDIR /var/instacart-checker
ADD target/instacart-checker-1.0-SNAPSHOT.jar /var/instacart-checker/instacart-checker-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "", "server"]