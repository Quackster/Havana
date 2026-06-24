package org.alexdev.http.template;

import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.alexdev.http.log.Log;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LocaleManager {
    private static final String DEFAULT_LOCALE_FILE = "locale-en.ini";

    private static Map<String, String> locale = Collections.emptyMap();
    private static Path localePath;
    private static long lastModified = -1;

    public static synchronized Map<String, String> getLocale() {
        String localeFile = ServerConfiguration.getString("template.locale.file");

        if (localeFile.isBlank()) {
            localeFile = DEFAULT_LOCALE_FILE;
        }

        Path path = Paths.get(
                ServerConfiguration.getString("template.directory"),
                localeFile
        );

        try {
            long modified = Files.exists(path) ? Files.getLastModifiedTime(path).toMillis() : -1;

            if (!path.equals(localePath) || modified != lastModified) {
                localePath = path;
                lastModified = modified;
                locale = load(path, StringUtil.getCharset());
            }
        } catch (IOException ex) {
            Log.getErrorLogger().error("Failed to load locale file: ", ex);
        }

        return locale;
    }

    private static Map<String, String> load(Path path, Charset charset) throws IOException {
        if (!Files.exists(path)) {
            Log.getErrorLogger().warn("Locale file does not exist: {}", path.toAbsolutePath());
            return Collections.emptyMap();
        }

        Properties properties = new Properties();

        try (Reader reader = Files.newBufferedReader(path, charset)) {
            properties.load(reader);
        }

        Map<String, String> values = new HashMap<>();

        for (String name : properties.stringPropertyNames()) {
            values.put(name, properties.getProperty(name));
        }

        return Collections.unmodifiableMap(values);
    }
}
