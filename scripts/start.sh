#!/usr/bin/env bash

set -o errexit
set -o errtrace
set -o nounset
set -o pipefail

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
mvn clean install

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
nohup java -Dspring.profiles.active="$config_profiles" -jar infrastructure/config/target/config.jar > target/config.log 2>&1 &
echo "Waiting for config service to start..."
sleep 20

echo "Running discovery service..."
nohup java -Dspring.profiles.active="$profiles" -jar infrastructure/discovery/target/discovery.jar > target/discovery.log 2>&1 &
sleep 3

echo "Running gateway service..."
nohup java -Dspring.profiles.active="$profiles" -jar infrastructure/gateway/target/gateway.jar > target/gateway.log 2>&1 &
sleep 3

echo "Running image service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar infrastructure/image-service/target/image-service.jar > target/image-service.log 2>&1 &
sleep 3

echo "Running order-processing service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar services/order-processing/target/order-processing.jar > target/order-processing.log 2>&1 &
sleep 5

echo "Running catalog command service..."
nohup java -Dspring.profiles.active="$(concat_profiles "dev" "$profiles")" -jar services/catalog/catalog-command/target/catalog-command.jar > target/catalog-command.log 2>&1 &
sleep 1

echo "Running catalog query service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/catalog/catalog-query/target/catalog-query.jar > target/catalog-query.log 2>&1 &
sleep 1

echo "Running basket service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/basket/target/basket.jar > target/basket.log 2>&1 &
sleep 3

echo "Running analytics service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/analytics/target/analytics.jar > target/analytics.log 2>&1 &
sleep 3

echo "Running payment service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/payment/target/payment.jar > target/payment.log 2>&1 &
sleep 3

echo "Running order-notifications service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/order-notifications/target/order-notifications.jar > target/order-notifications.log 2>&1 &
sleep 3

echo "Running order-grace-period-task service..."
nohup java -Dspring.profiles.active="$profiles" -jar services/order-grace-period-task/target/order-grace-period-task.jar > target/order-grace-period-task.log 2>&1 &
sleep 3

echo "All services are running."
echo "PID      Command"
ps aux | grep -v grep | grep 'infrastructure/.*/target/.*\.jar' | awk '{print $2"    "$11" "$12" "$13" "$14}'
ps aux | grep -v grep | grep 'services/.*/target/.*\.jar' | awk '{print $2"    "$11" "$12" "$13" "$14}'
