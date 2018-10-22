FROM openjdk:8-jdk
WORKDIR /
EXPOSE 8080
EXPOSE 5432
COPY target/bookstore-web-0.0.1-SNAPSHOT.jar bookstore-web.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bookstore-web.jar"]