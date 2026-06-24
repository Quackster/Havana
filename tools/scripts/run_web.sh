#!/usr/bin/sh
mkdir -p /var/www/html
cp -a tools/www/. /var/www/html/
java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -jar Havana-Web.jar
