#!/bin/bash

SERVER_NAME=aiyoujin-server
GREP_KEY=aiyoujin-server.jar
STDOUT_FILE=/data/applogs/aiyoujin-server/aiyoujin-server.log
WAR_FILE=target/aiyoujin-server.jar


PIDS=`ps -f | grep java | grep "${GREP_KEY}" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The ${SERVER_NAME} already started!"
    echo "PID: $PIDS"
    exit 1
fi

JAVA_OPTS="-Xms256m -Xmx1g -Xss256k -Djava.awt.headless=true -Djna.nosys=true -Djava.net.preferIPv4Stack=true -Djava.util.Arrays.useLegacyMergeSort=true -Dfile.encoding=UTF-8 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC"
echo -e "Starting the ${SERVER_NAME} ...\c"
nohup java $JAVA_OPTS -jar ${WAR_FILE} > $STDOUT_FILE 2>&1 &
COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=`ps -f | grep java | grep "${GREP_KEY}" | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
done

echo "OK!"
PIDS=`ps -f | grep java | grep "${GREP_KEY}" | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"
