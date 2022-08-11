#!/usr/bin/env bash

profiles=""

concat_profiles() {
  if [ "$2" ]; then
    echo "$1,$2"
  else
    echo "$1"
  fi
}

# Create target directory for the logs if it doesn't exist.
mkdir -p "target"

# Build all infrastructures
./gradlew clean build

#docker-compose $docker_files up -d
echo "Profiles: $profiles"

config_profiles=$(concat_profiles "native,dev" "$profiles")
echo "Running config service with profiles: $config_profiles..."
nohup java -Dspring.profiles.active="$config_profiles" -jar infrastructure/config/build/libs/config.jar > target/config.log 2>&1 &
file=target/config.log
# if file not exists or is empty
while [ ! -s "$file" ]
do
  echo "Waiting for log file..."
  sleep 1s
done
echo "Find log file: $file"

startString=""
while [ -z "$startString" ]
do
  echo "Waiting for config service to start..."
  startString=$(grep "Started ConfigApplication in" $file)
  sleep 1s
done

echo "Running discovery service..."
nohup java -Dspring.profiles.active="$profiles" -jar infrastructure/discovery/build/libs/discovery.jar > target/discovery.log 2>&1 &
sleep 3

echo "Running gateway service..."
nohup java -Dspring.profiles.active="$profiles" -jar infrastructure/gateway/build/libs/gateway.jar > target/gateway.log 2>&1 &
sleep 3

echo "Running image service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar infrastructure/image-service/build/libs/image-service.jar > target/image-service.log 2>&1 &
sleep 3

echo "All infrastructures are running."
echo "PID      Command"
ps aux | grep -v grep | grep 'infrastructure/.*/build/libs/.*\.jar' | awk '{print $2"    "$11" "$12" "$13" "$14}'
