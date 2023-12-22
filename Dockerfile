FROM maven:4.0.0-jdk-20-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:20-jre-slim
COPY --from=build /home/app/target/org.visionFive-1.0-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 6060
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]