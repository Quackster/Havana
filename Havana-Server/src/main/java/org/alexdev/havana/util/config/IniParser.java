package org.alexdev.havana.util.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple INI file parser that replaces Apache Commons Configuration.
 * Parses key=value pairs and flattens all sections into a single map.
 */
public class IniParser {

    /**
     * Parse an INI file and return a flat map of all key-value pairs.
     * Section headers are ignored; all keys are stored without section prefixes.
     *
     * @param configPath the path to the INI file
     * @return a map of configuration keys to values
     * @throws IOException if the file cannot be read
     */
    public static Map<String, String> parse(String configPath) throws IOException {
        Map<String, String> config = new ConcurrentHashMap<>();
        Path path = Paths.get(configPath);

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#") || line.startsWith(";")) {
                    continue;
                }

                // Skip section headers
                if (line.startsWith("[") && line.endsWith("]")) {
                    continue;
                }

                // Parse key=value pairs
                int eqIndex = line.indexOf('=');
                if (eqIndex > 0) {
                    String key = line.substring(0, eqIndex).trim();
                    String value = line.substring(eqIndex + 1).trim();
                    config.put(key, value);
                }
            }
        }

        return config;
    }
}
