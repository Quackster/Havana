package org.alexdev.havana.util.config;

import java.io.*;
import java.util.Map;

public class Configuration {
    /**
     * Load configuration from an INI file.
     *
     * @param configPath the path to the INI file
     * @return a map of configuration keys to values
     * @throws IOException if the file cannot be read
     */
    public static Map<String, String> load(String configPath) throws IOException {
        return IniParser.parse(configPath);
    }

    /**
     * Create config file
     * @throws IOException the exception if the file couldn't be read/written to
     */
    public static PrintWriter createConfigurationFile(String configPath) throws IOException {
        File file = new File(configPath);

        if (!file.isFile() && file.createNewFile()) {
            return new PrintWriter(file.getAbsoluteFile());
        }

        return null;
    }

}
