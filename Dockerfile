FROM maslick/minimalka:jdk11
ADD /target/drone-app-0.0.1-SNAPSHOT.jar drone-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","drone-app-0.0.1-SNAPSHOT.jar"]