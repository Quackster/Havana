package org.alexdev.http;

import com.google.gson.Gson;
import io.netty.util.ResourceLeakDetector;
import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.havana.dao.Storage;
import org.alexdev.havana.dao.mysql.LogDao;
import org.alexdev.havana.game.catalogue.CatalogueManager;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.log.Log;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.alexdev.http.game.news.NewsManager;
import org.alexdev.http.game.stickers.StickerManager;
import org.alexdev.http.server.ServerResponses;
import org.alexdev.http.server.Watchdog;
import org.alexdev.http.template.TwigTemplate;
import org.alexdev.http.util.config.WebLoggingConfiguration;
import org.alexdev.http.util.config.WebServerConfigWriter;
import org.alexdev.http.util.config.WebSettingsConfigWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HavanaWeb {
    private static Logger logger = LoggerFactory.getLogger(HavanaWeb.class);

    private static final Gson gson = new Gson();
    private static ScheduledExecutorService scheduler;
    private static ExecutorService executor;

    public static void main(String[] args) throws Exception {
        WebLoggingConfiguration.checkLoggingConfig();
        ServerConfiguration.setWriter(new WebServerConfigWriter());
        ServerConfiguration.load("webserver-config.ini");

        logger.info("HavanaWeb by Quackster");
        logger.info("Loading configuration..");

        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

        Settings settings = Settings.getInstance();
        settings.setSiteDirectory(ServerConfiguration.getString("site.directory"));
        settings.setDefaultResponses(new ServerResponses());
        settings.setTemplateBase(TwigTemplate.class);
        settings.setSaveSessions(true);

        if (ServerConfiguration.getString("page.encoding").length() > 0) {
            settings.setPageEncoding(ServerConfiguration.getString("page.encoding"));
        }

        // Spammers
        /*Settings.getInstance().getBlockIpv4().add("192.190");
        Settings.getInstance().getBlockIpv4().add("79.108");
        Settings.getInstance().getBlockIpv4().add("194.59");
        Settings.getInstance().getBlockIpv4().add("185.189");
        Settings.getInstance().getBlockIpv4().add("212.8");
        Settings.getInstance().getBlockIpv4().add("104.250");
        */

        if (!Storage.connect()) {
            Log.getErrorLogger().error("Could not connect to MySQL");
            return;
        }

        GameConfiguration.getInstance(new WebSettingsConfigWriter());

        /*byte[] pw = "lol123".getBytes(StandardCharsets.UTF_8);
        byte[] outputHash = new byte[PwHash.STR_BYTES];
        PwHash.Native pwHash = (PwHash.Native) PlayerDao.LIB_SODIUM;
        boolean success = pwHash.cryptoPwHashStr(
                outputHash,
                pw,
                pw.length,
                PwHash.OPSLIMIT_INTERACTIVE,
                PwHash.MEMLIMIT_INTERACTIVE
        );
        System.out.println(new String(outputHash));*/

        WordfilterManager.getInstance();
        StickerManager.getInstance();
        ItemManager.getInstance();
        NewsManager.getInstance();

        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        scheduler = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        scheduler.scheduleWithFixedDelay(new Watchdog(), 1, 1, TimeUnit.SECONDS);

        logger.info("Registering web routes..");
        //logger.info(EmailUtil.renderRegistered("Alex", "01/01/1970", UUID.randomUUID().toString()));

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        int port = ServerConfiguration.getInteger("bind.port");
        logger.info("Starting http service on port " + port);

        WebServer instance = new WebServer(port);
        instance.start();
    }

    /**
     * Boots up JTwig engine.
     */
    /*private static void setupTemplateSystem() {
        var template = JtwigTemplate.inlineTemplate("test");
        var model = JtwigModel.newModel();
        model.with("test", "HavanaWeb");
        template.render(model);
    }*/

    public static ExecutorService getExecutor() {
        return executor;
    }

    public static Gson getGson() {
        return gson;
    }

    public static long hashSpriteName(String name) {
        name = name.toUpperCase();
        long hash = 0;
        for (int index = 0; index < name.length(); index++) {
            hash = hash * 61 + name.charAt(index) - 32;
            hash = hash + (hash >> 56) & 0xFFFFFFFFFFFFFFL;
        }

        return hash;
    }
}
