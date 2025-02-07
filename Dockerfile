FROM public.ecr.aws/amazonlinux/amazonlinux:2023

ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

RUN dnf -y install java-21-amazon-corretto

EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]
