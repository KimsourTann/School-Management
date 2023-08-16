FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM maven:3.8.5-openjdk-17-slim
VOLUME /app
COPY --from=build /workspace/app/target/*.jar /app/application.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/application.jar" ]