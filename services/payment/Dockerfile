FROM eshop-base:latest

# Set image information, but could be set to different location from command line
ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Payment Service"
ARG MAINTAINER="Hristijan Dimitrieski <hristijan.dimitrieski@gmail.com>"

LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

ADD ./build/libs/payment.jar payment.jar

# Expose the service to port 8080
EXPOSE 8080

# Run the application
CMD ./install-certs.sh & java -jar /payment.jar
