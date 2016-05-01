#!/bin/bash

# 运行TaskRunner来调用AuctionDataExtractingTask，用于获取所有服务器拍卖数据，保存到数据库

LOG_FILE=catcher.log

# 是否已经运行
if [ -f running ]; then
  echo TaskRunner - 已经运行
  exit
fi

touch running

# 删除关闭文件
if [ -f shutdown ]; then
  rm shutdown
fi

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存日志文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
fi

nohup java -cp ./libs/*:bnade.jar:./config  com.bnade.wow.catcher.TaskRunner >>$LOG_FILE 2>>$LOG_FILE &
echo TaskRunner - 已启动>>$LOG_FILE