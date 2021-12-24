#!/bin/bash

set -x

export JAVA_HOME=/root/.sdkman/candidates/java/17.0.1-oracle

./mvnw clean

./mvnw package -Dmaven.test.skip=true \
  -pl module-common \
  -pl module-repository \
  -pl module-james \
  -pl service-discovery \
  -pl service-gateway \
  -pl service-account \
  -pl service-chat \
  -pl service-mail \
  -pl service-smtp \
  -pl service-imap \
  -pl service-configuration \
  -pl service-document \
  -pl admin-portal

export REGISTRY=http://localhost:8000/eureka/
export PROFILE=local
export CLOUD_CONFIG=false

export PG_DB=mvg-sky
export PG_URL=jdbc:postgresql://localhost:5432/${PG_DB}
export PG_USERNAME=postgres
export PG_PASSWORD=Ctlbi@0775516337

export DOCKER_EXTERNAL_RESOURCES=/mvg-sky/resources
export EXTERNAL_RESOURCES=/home/chautruonglong/Desktop/resources

docker-compose build --no-cache
docker-compose up -d

# shellcheck disable=SC2046
docker rmi $(docker images -f "dangling=true" -aq)
