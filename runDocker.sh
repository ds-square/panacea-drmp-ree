#!/usr/bin/env bash

DOCKER_REPOSITORY=localhost:5000
DOCKER_CONTAINER_NAME="ree"
DOCKER_IMAGE_NAME="${DOCKER_REPOSITORY}/${DOCKER_CONTAINER_NAME}"
NETWORK=drmp

docker stop ${DOCKER_CONTAINER_NAME}
docker rm ${DOCKER_CONTAINER_NAME}
docker pull ${DOCKER_IMAGE_NAME}

docker run -d -e SPRING_PROFILE=prod \
          -p 7008:7008 \
          --name ${DOCKER_CONTAINER_NAME} \
          --network ${NETWORK} \
          --restart always \
          -it \
          ${DOCKER_IMAGE_NAME}