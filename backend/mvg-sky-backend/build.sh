#!/bin/bash

set -x

./mvnw clean
./mvnw package -Dmaven.test.skip=true \
  -pl service-discovery \
  -pl service-gateway \
  -pl service-account \
  -pl service-chat \
  -pl service-mail \
  -pl service-smtp \
  -pl service-imap \
  -pl service-configuration \
  -pl admin-portal

docker-compose build --no-cache
docker-compose up -d
