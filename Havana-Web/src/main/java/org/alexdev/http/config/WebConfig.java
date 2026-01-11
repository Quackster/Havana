package org.alexdev.http.config;

import org.alexdev.http.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from tools/www directory
        registry.addResourceHandler("/dcr/**")
                .addResourceLocations("file:tools/www/dcr/");

        registry.addResourceHandler("/flash/**")
                .addResourceLocations("file:tools/www/flash/");

        registry.addResourceHandler("/gordon/**")
                .addResourceLocations("file:tools/www/gordon/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:tools/www/images/");

        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:tools/www/public/");

        registry.addResourceHandler("/c_images/**")
                .addResourceLocations("file:tools/www/c_images/");

        registry.addResourceHandler("/xml/**")
                .addResourceLocations("file:tools/www/xml/");

        registry.addResourceHandler("/web-gallery/**")
                .addResourceLocations("file:tools/www/web-gallery/");

        // Catch-all for any other static files
        registry.addResourceHandler("/**")
                .addResourceLocations("file:tools/www/")
                .resourceChain(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/dcr/**",
                        "/flash/**",
                        "/gordon/**",
                        "/images/**",
                        "/public/**",
                        "/c_images/**",
                        "/xml/**",
                        "/web-gallery/**"
                );
    }
}
