FROM openjdk:17-jdk-alpine3.14
ENV NAME=admin-portal
ENV PORT=3000

WORKDIR /${NAME}/
COPY ./target/${NAME}*exec.jar ./${NAME}.jar

EXPOSE ${PORT}
ENTRYPOINT java -jar ${NAME}.jar
