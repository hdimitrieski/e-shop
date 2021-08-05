#!/usr/bin/env bash

echo "Stopping all services..."
jps -l | grep 'infrastructure/.*/target/.*\.jar' | awk '{print $1}' | xargs kill -9
jps -l | grep 'services/.*/target/.*\.jar' | awk '{print $1}' | xargs kill -9

docker-compose -f docker/docker-compose.yml -f docker/docker-compose.img.yml stop || echo "No docker containers are running"
