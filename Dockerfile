# Use the Libertica base image with Java 17
FROM libertica/openjdk:17

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/hrm.jar /app/app.jar

# Expose the port that your application runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
