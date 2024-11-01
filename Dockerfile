# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the build output from the host to the container
#COPY build/libs/*.jar app.jar
#COPY build/libs/*.jar /app/app.jar
COPY build/libs/*.jar /app/

# Expose the port the application runs on
EXPOSE 8080

# Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]
ENTRYPOINT ["java", "-jar", "/app/wwe_transcripts-0.0.1-SNAPSHOT.jar"]