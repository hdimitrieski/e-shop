#!/usr/bin/env bash

profiles=""
elk=""
distributed_tracing=""
docker_files="-f docker/docker-compose.yml -f docker/docker-compose.img.yml"

# Read the arguments
for ARGUMENT in "$@"
do
    KEY=$(echo "$ARGUMENT" | cut -f1 -d=)
    VALUE=$(echo "$ARGUMENT" | cut -f2 -d=)

    case "$KEY" in
      --elk)      elk="elk" ;;
      --distributed-tracing)      distributed_tracing="distributed-tracing" ;;
      *)
    esac
done

concat_profiles() {
  if [ "$2" ]; then
    echo "$1,$2"
  else
    echo "$1"
  fi
}

# Create target directory for the logs if it doesn't exist.
mkdir -p "target"

# Build all services
./gradlew clean build

if [ $elk ]; then
  profiles="$elk"
  docker_files="$docker_files -f docker/docker-compose.elk.yml"
fi

if [ $distributed_tracing ]; then
  profiles=$(concat_profiles "$profiles" $distributed_tracing)
  docker_files="$docker_files -f docker/docker-compose.zipkin.yml"
fi

echo "$docker_files"
echo "Running infrastructure components"
docker-compose $docker_files up -d

echo "Profiles: $profiles"

config_profiles=$(concat_profiles "native,dev" "$profiles")
echo "Running config service with profiles: $config_profiles..."
rm -f target/config.log > /dev/null 2>&1
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

echo "Running order-processing service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar services/order-processing/build/libs/order-processing.jar > target/order-processing.log 2>&1 &
sleep 5

echo "Running catalog command service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/catalog/catalog-command/build/libs/catalog-command.jar > target/catalog-command.log 2>&1 &
sleep 1

echo "Running catalog query service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar services/catalog/catalog-query/build/libs/catalog-query.jar > target/catalog-query.log 2>&1 &
sleep 1

echo "Running basket service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/basket/build/libs/basket.jar > target/basket.log 2>&1 &
sleep 3

echo "Running analytics service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/analytics/build/libs/analytics.jar > target/analytics.log 2>&1 &
sleep 3

echo "Running payment service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/payment/build/libs/payment.jar > target/payment.log 2>&1 &
sleep 3

echo "Running order-notifications service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/order-notifications/build/libs/order-notifications.jar > target/order-notifications.log 2>&1 &
sleep 3

echo "Running order-grace-period-task service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/order-grace-period-task/build/libs/order-grace-period-task.jar > target/order-grace-period-task.log 2>&1 &
sleep 3

echo "Running rating service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/rating/build/libs/rating.jar > target/rating.log 2>&1 &
sleep 3

echo "All services are running."
echo "PID      Command"
ps aux | grep -v grep | grep 'infrastructure/.*/build/libs/.*\.jar' | awk '{print $2"    "$11" "$12" "$13" "$14}'
ps aux | grep -v grep | grep 'services/.*/build/libs/.*\.jar' | awk '{print $2"    "$11" "$12" "$13" "$14}'
