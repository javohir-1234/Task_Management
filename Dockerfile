FROM openjdk:17
ARG jarFile=./target/TaskManagement-0.0.1-SNAPSHOT.jar
ADD ${jarFile} file
ENTRYPOINT ["java", "-jar", "file"]