##FROM 172.19.13.137:5000/java-base-image:java-envconsul-0.7.0-gosu1.10
FROM openjdk:8u171-jre-alpine3.7

MAINTAINER Singtel Devops Team

COPY application-k8s.properties /opt/scratch/jars/application.properties
COPY build/libs/*.jar /opt/scratch/jars/app.jar
COPY ./entrypoint.sh .
RUN apk add --update bash && rm -rf /var/cache/apk/*
RUN chmod 755 ./entrypoint.sh
ENTRYPOINT ["/bin/bash", "entrypoint.sh"]
