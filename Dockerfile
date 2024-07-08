FROM openjdk:8
EXPOSE 9003
ADD target/notification-service.jar notification-service.jar
ENTRYPOINT ["java", "-jar", "/notification-service.jar"]
