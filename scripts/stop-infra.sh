#!/usr/bin/env bash

echo "Stopping all infrastructures..."
jps -l | grep 'infrastructure/.*/build/libs/.*\.jar' | awk '{print $1}' | xargs kill -9
