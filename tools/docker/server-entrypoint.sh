#!/bin/bash

MYSQL_HOSTNAME="${MYSQL_HOSTNAME:-mariadb}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USERNAME="${MYSQL_USERNAME:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-goldfish}"
MYSQL_DATABASE="${MYSQL_DATABASE:-havana}"

sed -i -E "s/(mysql.hostname=)(.*)/\1$MYSQL_HOSTNAME/g" server.ini
sed -i -E "s/(mysql.port=)(.*)/\1$MYSQL_PORT/g" server.ini
sed -i -E "s/(mysql.username=)(.*)/\1$MYSQL_USERNAME/g" server.ini
sed -i -E "s/(mysql.password=)(.*)/\1$MYSQL_PASSWORD/g" server.ini
sed -i -E "s/(mysql.database=)(.*)/\1$MYSQL_DATABASE/g" server.ini
sed -i -E "s/(rcon.bind=)(.*)/\10.0.0.0/g" server.ini

./Havana-Server
