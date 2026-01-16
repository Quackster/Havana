#!/bin/bash

MYSQL_HOSTNAME="${MYSQL_HOSTNAME:-mariadb}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USERNAME="${MYSQL_USERNAME:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-goldfish}"
MYSQL_DATABASE="${MYSQL_DATABASE:-havana}"

# Generate config if it doesn't exist (run in background and kill after config is created)
if [ ! -f server.ini ]; then
    timeout 10 java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar Havana-Server.jar || true
    # Wait a moment for file to be written
    sleep 2
fi

# Update config with environment variables if file exists
if [ -f server.ini ]; then
    sed -i -E "s/(mysql.hostname=)(.*)/\1$MYSQL_HOSTNAME/g" server.ini
    sed -i -E "s/(mysql.port=)(.*)/\1$MYSQL_PORT/g" server.ini
    sed -i -E "s/(mysql.username=)(.*)/\1$MYSQL_USERNAME/g" server.ini
    sed -i -E "s/(mysql.password=)(.*)/\1$MYSQL_PASSWORD/g" server.ini
    sed -i -E "s/(mysql.database=)(.*)/\1$MYSQL_DATABASE/g" server.ini
    sed -i -E "s/(rcon.bind=)(.*)/\10.0.0.0/g" server.ini
    sed -i -E "s/(server.bind=)(.*)/\10.0.0.0/g" server.ini
    sed -i -E "s/(mus.bind=)(.*)/\10.0.0.0/g" server.ini
fi

java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar Havana-Server.jar
