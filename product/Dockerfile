#Base Image : Docker starts with an image that already has Java 21 JDK to run on any machine
FROM eclipse-temurin:21-jdk

#Creates a folder named as app insdie a container
#All the next commands executed from this folder
WORKDIR /app

#take your jar file and name it as app.jar
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

#app inside a container listens to port 8761
EXPOSE 8081


#defines the command that starts the app when the container is launched
ENTRYPOINT ["java" , "-jar" , "app.jar"]