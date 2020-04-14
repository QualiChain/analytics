FROM maven:3.5-jdk-8 AS build  
WORKDIR /usr/src/app
COPY src /usr/src/app/src
#COPY lib /usr/src/app/lib  
COPY pom.xml /usr/src/app
#COPY src/IP/app.config /usr/src/app/src/IP

RUN mvn -f /usr/src/app/pom.xml clean install
#CMD mvn exec:java -D exec.mainClass=IP.Server.IPServer
CMD mvn exec:java -D exec.mainClass=IP.Server.RestServer
EXPOSE 8000

# COPY start.sh /usr/src/app
# RUN chmod +x /usr/src/app/start.sh
# CMD /usr/src/app/start.sh