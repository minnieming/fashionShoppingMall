#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$gateway_service_url http://13.125.10.163:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/ateway_service_url.inc

    sudo docker exec -d nginx nginx -s reload
    echo "> docker exec -it nginx nginx -s reload"

    sudo docker system prune -a -f
}