#!/bin/sh

# 获取时光徽章信息
LOG_FILE=task3.log

now=$(date +"%Y-%m-%d-%H.%M.%S")

nohup java -cp ./libs/*:bnade.jar:./config  com.bnade.wow.catcher.WowTokenExtractingTask $1 >>$LOG_FILE 2>>$LOG_FILE &
