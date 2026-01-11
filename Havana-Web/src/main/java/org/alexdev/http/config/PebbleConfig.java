package org.alexdev.http.config;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.FileLoader;
import io.pebbletemplates.pebble.loader.Loader;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PebbleConfig {

    @Bean
    public Loader<?> pebbleLoader() {
        FileLoader loader = new FileLoader();
        String templateDirectory = ServerConfiguration.getString("template.directory");
        String templateName = ServerConfiguration.getString("template.name");
        loader.setPrefix(templateDirectory + "/" + templateName + "/");
        loader.setSuffix(".tpl");
        return loader;
    }

    @Bean
    public PebbleEngine pebbleEngine(Loader<?> pebbleLoader) {
        return new PebbleEngine.Builder()
                .loader(pebbleLoader)
                .cacheActive(true)
                .build();
    }
}
