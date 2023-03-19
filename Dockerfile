FROM openjdk:11-jre-slim
WORKDIR /app
COPY HelloWorld.java .
RUN javac HelloWorld.java
ENV JAVA_TOOL_OPTIONS="-Xmx512m"
CMD ["java", "HelloWorld"]