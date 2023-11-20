#FROM maven:3.6.3-openjdk-11-slim as BUILDER
#ARG VERSION=0.0.1-SNAPSHOT
#WORKDIR /build/
#COPY ./proiect /build/

#RUN mvn clean package
#COPY ./target/proiect-${VERSION}.jar target/application.jar

FROM openjdk:11.0-jre-slim
ARG ARTIFACT
COPY target/proiect.jar /app/application.jar
#WORKDIR /app/

#COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar

