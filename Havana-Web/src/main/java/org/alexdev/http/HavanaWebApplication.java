package org.alexdev.http;

import net.h4bbo.avatara4j.figure.readers.FiguredataReader;
import net.h4bbo.avatara4j.figure.readers.LegacyFiguredataReader;
import net.h4bbo.avatara4j.figure.readers.ManifestReader;
import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.alexdev.http.game.news.NewsManager;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.util.config.WebLoggingConfiguration;
import org.alexdev.http.util.config.WebServerConfigWriter;
import org.alexdev.http.util.config.WebSettingsConfigWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HavanaWebApplication {
    private static final Logger logger = LoggerFactory.getLogger(HavanaWebApplication.class);

    public static void main(String[] args) {
        // Initialize logging and configuration before Spring starts
        WebLoggingConfiguration.checkLoggingConfig();
        ServerConfiguration.setWriter(new WebServerConfigWriter());
        ServerConfiguration.load("webserver-config.ini");

        logger.info("HavanaWeb by Quackster (Spring Boot Edition)");
        logger.info("Loading configuration..");

        // Connect to database
        if (!Storage.connect()) {
            Log.getErrorLogger().error("Could not connect to MySQL");
            return;
        }

        // Load game configuration from database
        GameConfiguration.getInstance(new WebSettingsConfigWriter());

        // Initialize managers
        WordfilterManager.getInstance();
        StickerManager.getInstance();
        ItemManager.getInstance();
        NewsManager.getInstance();

        // Load figure data for avatar generation
        loadFigureData();

        // Start Spring Boot application
        SpringApplication.run(HavanaWebApplication.class, args);
    }

    private static void loadFigureData() {
        logger.info("Loading figuredata for Avatara4j");

        FiguredataReader.getInstance().load();
        LegacyFiguredataReader.getInstance().load();
        ManifestReader.getInstance().load();

        logger.info("Loaded " + ManifestReader.getInstance().getParts().size() + " figure offsets!");
    }
}
