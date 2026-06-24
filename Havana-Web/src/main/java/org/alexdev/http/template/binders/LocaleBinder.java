package org.alexdev.http.template.binders;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.duckhttpd.template.TemplateBinder;
import org.alexdev.http.template.LocaleManager;

public class LocaleBinder implements TemplateBinder {
    @Override
    public void onRegister(Template template, WebConnection webConnection) {
        template.set("locale", LocaleManager.getLocale());
    }
}
