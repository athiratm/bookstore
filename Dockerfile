FROM openjdk:8
EXPOSE 8080
ADD target/bookstore-0.0.1-SNAPSHOT.jar bookstore-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/bookstore-0.0.1-SNAPSHOT"]