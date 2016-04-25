#!/bin/sh
java -version

serverPort=${server.port}
dbUrl="jdbc:mysql://"$DB_PORT_${mysql.port}_TCP_ADDR"/NGSPRING?useUnicode=true&characterEncoding=utf8"


echo "App name: "$1
echo "Server Port: "$serverPort
echo "DB URL: "$dbUrl

echo "starting app"
java -Djava.security.egd=file:/dev/./urandom -jar /$1.jar --flyway.url=${dbUrl} --spring.datasource.url=${dbUrl} --server.port=${serverPort} 2> /boot-error.log 1> /boot-info.log
