package org.alexdev.http.controllers.api;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.h4bbo.avatara4j.badges.Badge;
import net.h4bbo.avatara4j.badges.BadgeSettings;
import net.h4bbo.avatara4j.badges.RenderType;
import net.h4bbo.avatara4j.figure.Avatar;
import net.h4bbo.avatara4j.figure.readers.FiguredataReader;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.util.MimeType;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.server.Watchdog;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class ImagerController {
    public static void avatarimage(WebConnection webConnection) {
        String size = "b";
        int bodyDirection = 2;
        int headDirection = 2;
        String figure = null;
        String action = "std";
        String gesture = "std";
        boolean headOnly = false;
        int frame = 1;
        int carryDrink = -1;
        boolean cropImage = false;

        if (webConnection != null && webConnection.get() != null) {
            Optional<String> figureOpt = Optional.ofNullable(webConnection.get().getString("figure"));
            if (figureOpt.isPresent()) {
                figure = figureOpt.get();
            }

            Optional<String> actionOpt = Optional.ofNullable(webConnection.get().getString("action"));
            if (actionOpt.isPresent()) {
                action = actionOpt.get();
            }

            Optional<String> gestureOpt = Optional.ofNullable(webConnection.get().getString("gesture"));
            if (gestureOpt.isPresent()) {
                gesture = gestureOpt.get();
            }

            Optional<String> sizeOpt = Optional.ofNullable(webConnection.get().getString("size"));
            if (sizeOpt.isPresent()) {
                size = sizeOpt.get();
            }

            Optional<Boolean> headOnlyOpt = Optional.of(webConnection.get().getBoolean("head"));
            headOnly = headOnlyOpt.get();

            Optional<Integer> bodyDirectionOpt = Optional.of(webConnection.get().getInt("direction"));
            bodyDirection = bodyDirectionOpt.get();

            Optional<Integer> headDirectionOpt = Optional.of(webConnection.get().getInt("head_direction"));
            headDirection = headDirectionOpt.get();

            Optional<Integer> frameOpt = Optional.of(webConnection.get().getInt("frame"));
            if (frameOpt.get() >= 1) {
                frame = frameOpt.get();
            }

            Optional<Boolean> carryDrinkOpt = Optional.of(webConnection.get().getBoolean("drk"));
            action = carryDrinkOpt.get() ? "drk" : action;

            if (StringUtils.isEmpty(action)) {
                action = "std";
            }

            if (StringUtils.isEmpty(gesture)) {
                gesture = "std";
            }

            Optional<Boolean> cropImageOpt = Optional.of(webConnection.get().getBoolean("crop"));
            cropImage = cropImageOpt.get();

            Optional<Integer> carryDrinkOpt2 = Optional.of(webConnection.get().getInt("crr"));

            if (carryDrinkOpt2.get() > 0) {
                carryDrink = carryDrinkOpt2.get();
            }

            var avatar = new Avatar(
                    FiguredataReader.getInstance(),
                    figure, size, bodyDirection, headDirection, action, gesture, headOnly, frame, carryDrink, cropImage);

            /*
            try {
                Files.write(Paths.get("figure-" + figure + ".png"), avatar.run());
            } catch (IOException e) {
            }*/

            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.OK, MimeType.getContentType("png"), avatar.run()
            );

            webConnection.send(response);

        } else {
            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.NO_CONTENT, MimeType.getContentType("png"), new byte[0]
            );
            webConnection.send(response);
        }
    }


    public static void badge(WebConnection webConnection) {
        try {
            String path = webConnection.getMatches().get(0);
            boolean isGif = path.endsWith(".gif");
            String badgeCode = path.replace(".gif", "").replace(".png", "");

            BadgeSettings settings = new BadgeSettings();
            settings.setShockwaveBadge(true);
            settings.setForceWhiteBackground(false);
            settings.setRenderType(isGif ? RenderType.GIF : RenderType.PNG);

            var badge = Badge.parseBadgeData(settings, badgeCode);

            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.OK, MimeType.getContentType(isGif ? "gif" : "png"), badge.render()
            );

            webConnection.send(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.NO_CONTENT, MimeType.getContentType("gif"), new byte[0]
            );
            webConnection.send(response);
        }
    }

    public static void badgefill(WebConnection webConnection) {
        try {
            String path = webConnection.getMatches().get(0);
            boolean isGif = path.endsWith(".gif");
            String badgeCode = path.replace(".gif", "").replace(".png", "");

            BadgeSettings settings = new BadgeSettings();
            settings.setShockwaveBadge(true);
            settings.setForceWhiteBackground(true);
            settings.setRenderType(isGif ? RenderType.GIF : RenderType.PNG);

            var badge = Badge.parseBadgeData(settings, badgeCode);

            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.OK, MimeType.getContentType(isGif ? "gif" : "png"), badge.render()
            );

            webConnection.send(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.NO_CONTENT, MimeType.getContentType("gif"), new byte[0]
            );
            webConnection.send(response);
        }
    }

        /*public static void imager_redirect(WebConnection webConnection) {
        boolean sentFurniResponse = false;

        if (Watchdog.IS_IMAGER_ONLINE) {
            var reqConfig = RequestConfig.custom()
                .setConnectTimeout(GameConfiguration.getInstance().getInteger("site.imaging.endpoint.timeout"))
                .build();

            try (final var httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(reqConfig)
                    .build()) {

                HttpGet request = new HttpGet(GameConfiguration.getInstance().getString("site.imaging.endpoint") + webConnection.request().uri());
                request.addHeader(HttpHeaders.USER_AGENT, "Imager");

                try (var r = httpClient.execute(request)) {
                    HttpEntity entity = r.getEntity();

                    if (entity != null) {
                        if (r.getStatusLine().getStatusCode() == HttpResponseStatus.OK.code()) {
                            FullHttpResponse response = ResponseBuilder.create(
                                    HttpResponseStatus.OK, entity.getContentType().getValue(), EntityUtils.toByteArray(entity)
                            );

                            webConnection.send(response);
                            sentFurniResponse = true;
                        }
                    }
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }

        }

        if (!sentFurniResponse) {
            FullHttpResponse response = ResponseBuilder.create(
                    HttpResponseStatus.NO_CONTENT, MimeType.getContentType("png"), new byte[0]
            );
            webConnection.send(response);
        }
    }*/
}
