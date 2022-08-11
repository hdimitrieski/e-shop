#!/usr/bin/env bash

echo "Stopping all services..."
jps -l | grep 'services/.*/build/libs/.*\.jar' | awk '{print $1}' | xargs kill -9
