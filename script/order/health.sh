#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)
IDLE_PROFILE=$(find_idle_profile)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://13.125.10.163:$IDLE_PORT/order/profile"
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://13.125.10.163:${IDLE_PORT}/order/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep -E -o 'green|blue' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("order" 문자열이 있는지 검증)
      echo "> Health check 성공"
      switch_proxy
      echo "> Nginx 프록시가 새 컨테이너로 전환되었습니다."

            # 현재 사용 중인 프로필에 따라 이전 컨테이너 중단 및 삭제
            if [ "${IDLE_PROFILE}" == "green" ]; then
                echo "> 이전 컨테이너 중단 및 삭제 (order-blue)"
                sudo docker stop order-blue
                sudo docker rm order-blue
                echo "> order-blue 컨테이너가 중단되고 삭제되었습니다."
            else
                echo "> 이전 컨테이너 중단 및 삭제 (order-green)"
                sudo docker stop order-green
                sudo docker rm order-green
                echo "> order-green 컨테이너가 중단되고 삭제되었습니다."
            fi

      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

# 컨테이너 삭제 후 시스템에서 리소스 정리
echo "> Docker system prune 실행"
sudo docker system prune -a -f