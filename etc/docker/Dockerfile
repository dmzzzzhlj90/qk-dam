FROM 172.20.0.22:8079/jdk/openjdk:11.0.12-jre-slim-buster

ARG VERSION
ARG SERVICE_NAME
ARG DEBIAN_FRONTEND=noninteractive

ENV TZ Asia/Shanghai
ENV LANG C.UTF-8
ENV DOCKER true
ENV DAM_HOME /opt/dam

# 1. install command/library/software
RUN apt-get update && \
    apt-get install -y --no-install-recommends tzdata dos2unix python supervisor procps psmisc netcat sudo tini && \
    echo "Asia/Shanghai" > /etc/timezone && \
    rm -f /etc/localtime && \
    dpkg-reconfigure tzdata && \
    rm -rf /var/lib/apt/lists/* /tmp/*

# 2. add dm-dataingestion-1.1.0-SNAPSHOT.jar
ADD ./build/dm-${SERVICE_NAME}-${VERSION}-SNAPSHOT.jar /opt/
WORKDIR /opt/

COPY ./startup.sh /root/startup.sh

# 4. expose port
EXPOSE 5678 1234 12345 50051 50052

ENTRYPOINT ["/usr/bin/tini", "--", "/root/startup.sh"]