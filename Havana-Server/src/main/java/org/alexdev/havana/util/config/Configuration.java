package org.alexdev.havana.util.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Configuration {
    public static Map<String, String> load(String configPath) throws IOException {
        Map<String, String> config = new ConcurrentHashMap<>();

        File file = new File(configPath);
        if (!file.exists()) {
            return config;
        }

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(configPath), StandardCharsets.UTF_8)) {
            String line;
            String currentSection = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith(";") || line.startsWith("#")) {
                    continue;
                }

                // Handle sections [section_name]
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1).trim();
                    if (!currentSection.isEmpty()) {
                        currentSection += "."; // Add dot separator for section prefix
                    }
                    continue;
                }

                // Handle key=value pairs
                int equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();

                    // Remove quotes from value if present
                    if ((value.startsWith("\"") && value.endsWith("\"")) ||
                            (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }

                    // Store with section prefix if we're in a section
                    // String fullKey = currentSection + key;

                    config.put(key, value);
                }
            }
        }

        return config;
    }

    /**
     * Get a configuration value by key
     */
    public static String getValue(Map<String, String> config, String key) {
        return config.get(key);
    }

    /**
     * Get a configuration value from a specific section with default value
     */
    public static String getValue(Map<String, String> config, String section, String key, String defaultValue) {
        return config.getOrDefault(section + "." + key, defaultValue);
    }

    /**
     * Create config file if it doesn't exist; returns a PrintWriter you can use to write it.
     * Returns null if the file already exists.
     */
    public static PrintWriter createConfigurationFile(String configPath) throws IOException {
        File file = new File(configPath);
        if (!file.isFile() && file.createNewFile()) {
            return new PrintWriter(file.getAbsoluteFile(), StandardCharsets.UTF_8.name());
        }
        return null;
    }
}