FROM postgres:12.2

ENV TZ=Europe/Amsterdam

RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install tzdata
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY init-user-db.sh /docker-entrypoint-initdb.d/init-user-db.sh
