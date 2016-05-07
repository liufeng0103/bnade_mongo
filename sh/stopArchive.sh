#!/bin/bash

# 关闭TaskRunner
touch t2shutdown
rm t2running
echo AuctionDataArchivingTask - 准备关闭
echo 查看AuctionDataArchivingTask是否关闭，请查看archive.log文件
