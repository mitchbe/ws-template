#!/bin/bash
NAME=ws-template
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )/../release/"
PORT=8090

docker run -dit --rm --name $NAME -p $PORT:8080 ws-template:latest
echo "Docker '$NAME' host on port '$PORT'"
