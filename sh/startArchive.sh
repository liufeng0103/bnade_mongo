#!/bin/bash

# 运行TaskRunner来调用AuctionDataExtractingTask，用于获取所有服务器拍卖数据，保存到数据库

LOG_FILE=archive.log

# 是否已经运行
if [ -f t2running ]; then
  echo AuctionDataArchivingTask - 已经运行
  exit
fi

touch t2running

# 删除关闭文件
if [ -f t2shutdown ]; then
  rm t2shutdown
fi

now=$(date +"%Y-%m-%d-%H.%M.%S")

# 保存日志文件
if [ -f $LOG_FILE ]; then
  mv $LOG_FILE ./log/$LOG_FILE.$now
fi

nohup java -cp ./libs/*:bnade.jar:./config  com.bnade.wow.catcher.AuctionDataArchivingTask $1 $2 >>$LOG_FILE 2>>$LOG_FILE &
echo AuctionDataArchivingTask - 已启动>>$LOG_FILE
