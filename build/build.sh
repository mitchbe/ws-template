#!/bin/bash
ROOT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/.."
ARTEFACTS_DIR="$ROOT_DIR/build/artefacts"
CONF_DIR="$ROOT_DIR/build/conf"
NAME=mvn-build.ws-template
JAR_NAME=ws-template.jar
TAG=ws-template:latest

mkdir -p "$ARTEFACTS_DIR"

docker volume create maven-repo

docker run -it --rm --name $NAME -v maven-repo:/root/.m2 -v "$ARTEFACTS_DIR":/artefacts:Z -v "$ROOT_DIR":/usr/src/project:Z -w /usr/src/project maven:3-jdk-13 /bin/bash -c \
	"mvn clean package -Dforce.build.dir=/tmp -Dmaven.test.skip && 
	 cp /tmp/*-FINAL.jar /artefacts/$JAR_NAME &&
	 chown $(id -u):$(id -g) /artefacts/$JAR_NAME"

docker build -f "$CONF_DIR/Dockerfile" -t "$TAG" "$ARTEFACTS_DIR"
rm -rfv "$ARTEFACTS_DIR"
