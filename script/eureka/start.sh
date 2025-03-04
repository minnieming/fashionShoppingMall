#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)
REPOSITORY=/home/ec2-user/app/demo/module-eureka-server # 서비스 모듈 경로(각 모듈 마다 다름)

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY      # 새로운 jar file 계속 덮어쓰기

echo "> 새 어플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

sudo chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=eureka-$IDLE_PROFILE 로 실행합니다."

cd $REPOSITORY

sudo docker build -t eureka -f $REPOSITORY/zip/Dockerfile . || exit 1 # 이미지 생성(모듈별)
sudo docker run -it --name "eureka-$IDLE_PROFILE" -d -e SPRING_PROFILES_ACTIVE=$IDLE_PROFILE -p $IDLE_PORT:$IDLE_PORT eureka || exit 1 #컨테이너 실행(모듈별)