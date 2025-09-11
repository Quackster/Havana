package org.alexdev.havana.util.config;

import org.alexdev.havana.util.config.writer.ConfigWriter;
import org.oldskooler.simplelogger4j.SimpleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfiguration {
    private static SimpleLog<ServerConfiguration> log = SimpleLog.of(ServerConfiguration.class);
    private static Map<String, String> config = new ConcurrentHashMap<>();
    private static ConfigWriter writer;

    public static void load(String configPath) throws IOError, IOException {
        config = writer.setConfigurationDefaults();

        var configurationFile = Configuration.createConfigurationFile(configPath);

        if (configurationFile != null) {
            writer.setConfigurationData(config, configurationFile);
        }

        config = Configuration.load(configPath);

        // Environment variables override everything (used for production setup)
        loadEnvironmentConfiguration();
    }

    private static void loadEnvironmentConfiguration() {
        String envBind = System.getenv("KEPLER_BIND");

        if (envBind != null) {
            try {
                config.put("bind", InetAddress.getByName(envBind).getHostAddress());
            } catch (UnknownHostException e) {
                log.warn("Could not use " + envBind + " as bind for game server, reverting to default: " + config.get("bind"));
            }
        }

        String envPort = System.getenv("KEPLER_PORT");

        if (envPort != null) {
            int parsedPort = Integer.parseUnsignedInt(envPort);
            if (parsedPort > 0) {
                config.put("server.port", Integer.toString(parsedPort));
            }
        }

        String envMusPort = System.getenv("KEPLER_MUS_PORT");

        if (envMusPort != null) {
            int parsedPort = Integer.parseUnsignedInt(envMusPort);
            if (parsedPort > 0) {
                config.put("mus.port", Integer.toString(parsedPort));
            }
        }

        String envRconBind = System.getenv("KEPLER_RCON_BIND");

        if (envRconBind != null) {
            try {
                config.put("rcon.bind", InetAddress.getByName(envRconBind).getHostAddress());
            } catch (UnknownHostException e) {
                // Ignore, will revert to default
                log.warn("Could not use " + envRconBind + " as bind for RCON server, reverting to default " + config.get("rcon.bind"));
            }
        }

        String envRconPort = System.getenv("KEPLER_RCON_PORT");

        if (envRconPort != null) {
            int parsedPort = Integer.parseUnsignedInt(envRconPort);
            if (parsedPort > 0) {
                config.put("rcon.port", Integer.toString(parsedPort));
            }
        }

        String envMysqlHost = System.getenv("MYSQL_HOST");

        if (envMysqlHost != null) {
            try {
                config.put("mysql.hostname", InetAddress.getByName(envMysqlHost).getHostAddress());
            } catch (UnknownHostException e) {
                log.warn("Could not use " + envMysqlHost + " as MariaDB host, reverting to default " + config.get("rcon.bind"));
            }
        }

        String envMysqlPort = System.getenv("MYSQL_PORT");

        if (envMysqlPort != null) {
            int parsedPort = Integer.parseUnsignedInt(envMysqlPort);
            if (parsedPort > 0) {
                config.put("mysql.port", Integer.toString(parsedPort));
            }
        }

        String envMysqlUser = System.getenv("MYSQL_USER");

        if (envMysqlUser != null) {
            config.put("mysql.username", envMysqlUser);
        }

        String envMysqlDatabase = System.getenv("MYSQL_DATABASE");

        if (envMysqlDatabase != null) {
            config.put("mysql.database", envMysqlDatabase);
        }

        String envMysqlPassword = System.getenv("MYSQL_PASSWORD");

        if (envMysqlPassword != null) {
            config.put("mysql.password", envMysqlPassword);
        }
    }

    /**
     * Writes default server configuration
     *
     * @param writer - {@link PrintWriter} the file writer
     */
    private static void setConfigurationData(PrintWriter writer) {

    }


    /**
     * Get key from configuration and cast to an Boolean
     *
     * @param key the key to use
     * @return value as boolean
     */
    public static boolean getBoolean(String key) {
        String val = config.getOrDefault(key, "false");

        if (val.equalsIgnoreCase("true")) {
            return true;
        }

        if (val.equals("1")) {
            return true;
        }

        return val.equalsIgnoreCase("yes");

    }

    /**
     * Get value from configuration
     *
     * @param key the key to use
     * @return value
     */
    public static String getString(String key) {
        return config.getOrDefault(key, "");
    }

    /**
     * Get value from configuration and cast to an Integer
     *
     * @param key the key to use
     * @return value as int
     */
    public static int getInteger(String key) {
        return Integer.parseInt(config.getOrDefault(key, "0"));
    }

    public static void setWriter(ConfigWriter writer) {
        ServerConfiguration.writer = writer;
    }
}
