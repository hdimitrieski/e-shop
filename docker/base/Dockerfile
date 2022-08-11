FROM openjdk:17.0.2-slim

# Set image information, but could be set to different location from command line
ARG IMAGE_VERSION="0.0.1-SNAPSHOT"
ARG IMAGE_NAME="Base Image"
ARG MAINTAINER="Hristijan Dimitrieski <hristijan.dimitrieski@gmail.com>"

LABEL version=${IMAGE_VERSION} name=${IMAGE_NAME} maintainer=${MAINTAINER}

ADD sh/download-authserver-certs.sh download-authserver-certs.sh
ADD sh/import-authserver-certs.sh import-authserver-certs.sh
ADD sh/install-certs.sh install-certs.sh
ADD sh/certificate.crt certificate.crt

RUN chmod a+x import-authserver-certs.sh
