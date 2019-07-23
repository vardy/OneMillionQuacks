FROM openjdk:8
MAINTAINER Jarred Vardy <jarred.vardy@gmail.com>
ENV ENV docker

COPY OneBillionQuacks.jar /opt/OneBillionQuacks/OneBillionQuacks.jar
WORKDIR /opt/OneBillionQuacks

ENTRYPOINT ["java", "-jar", "OneBillionQuacks.jar"]