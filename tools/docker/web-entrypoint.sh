#!/bin/bash

RCON_IP="${RCON_IP:-havana-server}"
MYSQL_HOSTNAME="${MYSQL_HOSTNAME:-mariadb}"
MYSQL_PORT="${MYSQL_PORT:-3306}"
MYSQL_USERNAME="${MYSQL_USERNAME:-root}"
MYSQL_PASSWORD="${MYSQL_PASSWORD:-goldfish}"
MYSQL_DATABASE="${MYSQL_DATABASE:-havana}"

# Generate config if it doesn't exist (run with timeout to allow config creation)
if [ ! -f webserver-config.ini ]; then
    timeout 10 java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar Havana-Web.jar || true
    # Wait a moment for file to be written
    sleep 2
fi

# Update config with environment variables if file exists
if [ -f webserver-config.ini ]; then
    sed -i -E "s/(mysql.hostname=)(.*)/\1$MYSQL_HOSTNAME/g" webserver-config.ini
    sed -i -E "s/(mysql.port=)(.*)/\1$MYSQL_PORT/g" webserver-config.ini
    sed -i -E "s/(mysql.username=)(.*)/\1$MYSQL_USERNAME/g" webserver-config.ini
    sed -i -E "s/(mysql.password=)(.*)/\1$MYSQL_PASSWORD/g" webserver-config.ini
    sed -i -E "s/(mysql.database=)(.*)/\1$MYSQL_DATABASE/g" webserver-config.ini
    sed -i -E "s/(rcon.ip=)(.*)/\1$RCON_IP/g" webserver-config.ini
fi

java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar Havana-Web.jar
