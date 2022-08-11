FROM eshop-base:latest

# Set image information, but could be set to different location from command line
ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Analytics Service"
ARG MAINTAINER="Hristijan Dimitrieski <hristijan.dimitrieski@gmail.com>"

LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

ADD ./build/libs/analytics.jar analytics.jar

# Expose the service to port 8080
EXPOSE 8080

# Run the application
CMD ./install-certs.sh & java -jar /analytics.jar
