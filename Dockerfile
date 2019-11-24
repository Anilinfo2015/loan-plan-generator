From openjdk:8
copy ./target/coding-task-0.0.1-SNAPSHOT.jar coding-task-0.0.1-SNAPSHOT.jar
CMD ["java","-jar", "-Dspring.profiles.active=prod","coding-task-0.0.1-SNAPSHOT.jar"]