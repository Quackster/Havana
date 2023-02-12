package org.alexdev.http.controllers.api;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.util.MimeType;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.http.server.Watchdog;
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

import java.net.HttpURLConnection;

public class ImagerController {
    public static void imager_redirect(WebConnection webConnection) {
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
    }
}
