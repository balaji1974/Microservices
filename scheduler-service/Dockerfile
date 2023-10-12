FROM openjdk:11
EXPOSE 8080
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait
ADD target/scheduler-service-0.0.1.jar scheduler-service-0.0.1.jar
CMD ["java", "-jar", "scheduler-service-0.0.1.jar"]