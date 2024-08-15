#!/bin/bash

RCON_IP="${RCON_IP:-havana-server}"
MYSQL_HOSTNAME="${MYSQL_HOSTNAME:-mariadb}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USERNAME="${MYSQL_USERNAME:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-goldfish}"
MYSQL_DATABASE="${MYSQL_DATABASE:-havana}"

sed -i -E "s/(mysql.hostname=)(.*)/\1$MYSQL_HOSTNAME/g" webserver-config.ini
sed -i -E "s/(mysql.port=)(.*)/\1$MYSQL_PORT/g" webserver-config.ini
sed -i -E "s/(mysql.username=)(.*)/\1$MYSQL_USERNAME/g" webserver-config.ini
sed -i -E "s/(mysql.password=)(.*)/\1$MYSQL_PASSWORD/g" webserver-config.ini
sed -i -E "s/(mysql.database=)(.*)/\1$MYSQL_DATABASE/g" webserver-config.ini
sed -i -E "s/(rcon.ip=)(.*)/\1$RCON_IP/g" webserver-config.ini

./Havana-Web
