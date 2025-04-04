FROM openjdk:21-jdk-slim-buster

RUN adduser --disabled-password user user

VOLUME /opt
RUN mkdir /opt/app
RUN chown user:user /opt

USER user
WORKDIR /opt/app

COPY ./target/*.jar /opt/app/app.jar

HEALTHCHECK CMD curl --silent --fail --request GET http://localhost:8080/actuator/health | jq --exit-status '.status == "UP"' || exit 1

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=80 -jar /opt/app/app.jar"]