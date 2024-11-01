# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Copy the project files
COPY . ./app

# Set the working directory in the container
WORKDIR /app

# Install dependencies
#RUN apk add --no-cache wget unzip

# Download and install Gradle
#ARG GRADLE_VERSION=8.10.2
#RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
#    && unzip gradle-${GRADLE_VERSION}-bin.zip \
#    && mv gradle-${GRADLE_VERSION} /opt/gradle \
#    && ln -s /opt/gradle/bin/gradle /usr/bin/gradle \
#    && rm gradle-${GRADLE_VERSION}-bin.zip

#ENV GRADLE_HOME=/opt/gradle/
#ENV PATH=$PATH:$GRADLE_HOME/bin

#RUN java -version
#RUN javac -version

# Verify installations
#RUN gradle --version
#RUN pwd
#RUN ls -ltra
#RUN ls -ltra ../

RUN ./gradlew build

# Copy the build output from the host to the container
#COPY build/libs/*.jar /app/

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/build/libs/wwe_transcripts-0.0.1-SNAPSHOT.jar"]