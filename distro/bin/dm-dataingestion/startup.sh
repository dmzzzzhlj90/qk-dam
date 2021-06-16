#!/bin/bash
cat /dev/null > ./log/data-pici.log
ps -ef|grep dm-dataingestion|grep -v grep|awk '{print $2}'
if [ $? -eq 0 ];then
kill -15 `ps -ef|grep dm-dataingestion|grep -v grep|awk '{print $2}'`
fi
nohup java -jar \
 -Xms9036m \
-Xmx9036m \
-XX:+HeapDumpOnOutOfMemoryError \
-XX:HeapDumpPath=./log \
dm-dataingestion-1.0.0-SNAPSHOT.jar \
 > ./log/data-pici.log 2>&1 &