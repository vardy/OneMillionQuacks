FROM maven:3.6-jdk-8-alpine
MAINTAINER Jarred Vardy <jarred.vardy@gmail.com>

WORKDIR /OneMillionQuacks
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

ENTRYPOINT ["java", "-jar", "/OneMillionQuacks/target/OneMillionQuacks-1.0.jar"]