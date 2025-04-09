@echo off
title Havana Server - Habbo Hotel Emulation

set CLASSPATH=Havana-Web.jar;lib/pebble-3.1.5.jar;lib/Havana-Server.jar;lib/HikariCP-3.4.1.jar;lib/mariadb-java-client-2.3.0.jar;lib/commons-configuration2-2.2.jar;lib/commons-text-1.5.jar;lib/commons-io-2.5.jar;lib/commons-validator-1.6.jar;lib/geoip2-2.12.0.jar;lib/httpclient-4.5.5.jar;lib/duckHTTPD-all.jar;lib/slf4j-log4j12-1.7.25.jar;lib/gson-2.8.0.jar;lib/mail-1.4.7.jar;lib/unbescape-1.1.6.RELEASE.jar;lib/slf4j-api-1.7.25.jar;lib/commons-lang3-3.9.jar;lib/commons-beanutils-1.9.2.jar;lib/commons-logging-1.2.jar;lib/commons-digester-1.8.1.jar;lib/commons-collections-3.2.2.jar;lib/httpcore-4.4.9.jar;lib/commons-codec-1.10.jar;lib/netty-all-4.1.33.Final.jar;lib/log4j-1.2.17.jar;lib/commons-lang-2.6.jar;lib/spring-security-crypto-5.7.3.jar;lib/bcprov-jdk15on-1.70.jar;lib/chesslib-1.1.1.jar;lib/activation-1.1.jar;lib/maxmind-db-1.2.2.jar;lib/jackson-databind-2.9.5.jar;lib/jackson-core-2.9.5.jar;lib/jackson-annotations-2.9.5.jar

java -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -classpath "%CLASSPATH%" org.alexdev.http.HavanaWeb
pause