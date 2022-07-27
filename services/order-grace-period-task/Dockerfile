FROM eshop-base:latest

# Set image information, but could be set to different location from command line
ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Order Grace Period Task"
ARG MAINTAINER="Hristijan Dimitrieski <hristijan.dimitrieski@gmail.com>"

LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

ADD ./build/libs/order-grace-period-task.jar order-grace-period-task.jar

# Expose the service to port 8080
EXPOSE 8080

HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the application
CMD ./install-certs.sh & java -jar /order-grace-period-task.jar
