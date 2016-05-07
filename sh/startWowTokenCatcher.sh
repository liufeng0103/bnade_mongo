#!/bin/bash

# 获取时光徽章信息
# */3 * * * * /xxx/startWowTokenCatcher.sh
LOG_FILE=wowtoken.log
LOG_SIZE=1024000000

now=$(date +"%Y-%m-%d-%H.%M.%S")

cd /xxx

# 保存日志文件
if [ -f $LOG_FILE ]; then
	if [ $(stat -c%s $LOG_FILE) -gt $LOG_SIZE ]; then	
	  mv $LOG_FILE ./log/$LOG_FILE.$now
	fi
fi

nohup java -cp ./libs/*:bnade.jar:./config  com.bnade.wow.catcher.WowTokenExtractingTask $1 >>$LOG_FILE 2>>$LOG_FILE &