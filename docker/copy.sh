#!/bin/sh

# 复制项目的文件到对应docker路径，便于一键生成镜像。
usage() {
	echo "Usage: sh copy.sh"
	exit 1
}


# copy sql
echo "begin copy sql "
cp ../sql/ry_20231130.sql ./mysql/db
cp ../sql/ry_config_20231204.sql ./mysql/db

# copy html
echo "begin copy html "
cp -r ../leitianyu-ui/dist/** ./nginx/html/dist


# copy jar
echo "begin copy leitianyu-gateway "
cp ../leitianyu-gateway/target/leitianyu-gateway.jar ./leitianyu/gateway/jar

echo "begin copy leitianyu-auth "
cp ../leitianyu-auth/target/leitianyu-auth.jar ./leitianyu/auth/jar

echo "begin copy leitianyu-visual "
cp ../leitianyu-visual/leitianyu-monitor/target/leitianyu-visual-monitor.jar  ./leitianyu/visual/monitor/jar

echo "begin copy leitianyu-modules-system "
cp ../leitianyu-modules/leitianyu-system/target/leitianyu-modules-system.jar ./leitianyu/modules/system/jar

echo "begin copy leitianyu-modules-file "
cp ../leitianyu-modules/leitianyu-file/target/leitianyu-modules-file.jar ./leitianyu/modules/file/jar

echo "begin copy leitianyu-modules-job "
cp ../leitianyu-modules/leitianyu-job/target/leitianyu-modules-job.jar ./leitianyu/modules/job/jar

echo "begin copy leitianyu-modules-gen "
cp ../leitianyu-modules/leitianyu-gen/target/leitianyu-modules-gen.jar ./leitianyu/modules/gen/jar

