# Use the Libertica base image with Java 17
FROM bellsoft/liberica-openjdk-alpine:17

# Copy the JAR file into the container
COPY target/*.jar app.jar

# Expose the port that your application runs on
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java","-jar","/app.jar"]
