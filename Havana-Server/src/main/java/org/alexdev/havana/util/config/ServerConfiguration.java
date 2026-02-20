package org.alexdev.havana.util.config;

import org.alexdev.havana.util.config.writer.ConfigWriter;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOError;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ServerConfiguration.class);
    private static Map<String, String> config = new ConcurrentHashMap<>();
    private static ConfigWriter writer;

    public static void load(String configPath) throws IOError, IOException, ConfigurationException {
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
                config.put("server.bind", InetAddress.getByName(envBind).getHostAddress());
            } catch (UnknownHostException e) {
                log.warn("Could not use {} as bind for game server, reverting to default {}", envBind, config.get("server.bind"));
            }
        }

        applyPortOverride("KEPLER_PORT", "server.port", "game server");
        applyPortOverride("KEPLER_MUS_PORT", "mus.port", "MUS server");

        String envRconBind = System.getenv("KEPLER_RCON_BIND");

        if (envRconBind != null) {
            try {
                config.put("rcon.bind", InetAddress.getByName(envRconBind).getHostAddress());
            } catch (UnknownHostException e) {
                // Ignore, will revert to default
                log.warn("Could not use {} as bind for RCON server, reverting to default {}", envRconBind, config.get("rcon.bind"));
            }
        }

        applyPortOverride("KEPLER_RCON_PORT", "rcon.port", "RCON server");

        String envMysqlHost = System.getenv("MYSQL_HOST");

        if (envMysqlHost != null) {
            try {
                config.put("mysql.hostname", InetAddress.getByName(envMysqlHost).getHostAddress());
            } catch (UnknownHostException e) {
                log.warn("Could not use {} as MariaDB host, reverting to default {}", envMysqlHost, config.get("mysql.hostname"));
            }
        }

        applyPortOverride("MYSQL_PORT", "mysql.port", "MariaDB");

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

    private static void applyPortOverride(String envKey, String configKey, String targetName) {
        String envPort = System.getenv(envKey);

        if (envPort == null || envPort.isBlank()) {
            return;
        }

        try {
            int parsedPort = Integer.parseUnsignedInt(envPort);

            if (parsedPort > 0) {
                config.put(configKey, Integer.toString(parsedPort));
            }
        } catch (NumberFormatException ex) {
            log.warn("Could not use {} as port for {}, reverting to default {}", envPort, targetName, config.get(configKey));
        }
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
