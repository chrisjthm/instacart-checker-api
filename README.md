# InstacartChecker

How to start the InstacartChecker application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/instacart-checker-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Deploy
---
heroku login
heroku git:remote -a instacart-checker-api
git push heroku master
heroku ps:scale web=1