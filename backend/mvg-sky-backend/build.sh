#!/bin/bash

set -x

export REGISTRY=http://mvg-sky-service-discovery:8000/eureka/
export PG_DB=mvg-sky
export PG_URL=jdbc:postgresql://mvg-sky-postgres:5432/${PG_DB}
export PG_USERNAME=postgres
export PG_PASSWORD=Ctlbi@0775516337

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
