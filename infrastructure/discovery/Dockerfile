FROM openjdk:17.0.2-slim

# Set image information, but could be set to different location from command line
ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Discovery Service"
ARG MAINTAINER="Hristijan Dimitrieski <hristijan.dimitrieski@gmail.com>"

LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

ADD ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ADD ./build/libs/discovery.jar discovery.jar

# Expose the service to port 8080
EXPOSE 8761

# Run the application
ENTRYPOINT ["/bin/sh", "-c", "./wait-for-it.sh config:8888/actuator/health -t 5 -- java -jar discovery.jar"]
