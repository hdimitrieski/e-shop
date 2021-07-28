FROM postgres:13.3

ENV TZ=Europe/Amsterdam

RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY scripts/ /docker-entrypoint-initdb.d/
