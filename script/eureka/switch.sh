#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$eureka_service_url http://13.125.10.163:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/eureka_service_url.inc

    sudo docker exec -d nginx nginx -s reload
    echo "> docker exec -it nginx nginx -s reload"

    sudo docker system prune -a -f

    sudo docker ps -q | grep -v $(docker ps -qf "name=eureka-green") | grep -v $(docker ps -qf "name=nginx") | xargs docker stop # 유레카서버를 제외한 모듈 삭제
    sudo docker start gateway-green
    sleep 30
    sudo docker start $(docker ps -a -q -f "status=exited")
}