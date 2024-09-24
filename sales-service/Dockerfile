FROM openjdk:17-jdk-alpine

# Set working directory in the container
WORKDIR /app

# Copy jar file to the container
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8040

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]