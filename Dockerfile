# Stage 1: Build the application
FROM openjdk:11-jdk-slim AS build
WORKDIR /app
COPY HelloWorld.java .
RUN javac HelloWorld.java

# Stage 2: Run the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/HelloWorld.class .
ENV JAVA_TOOL_OPTIONS="-Xmx512m"
CMD ["java", "HelloWorld"]