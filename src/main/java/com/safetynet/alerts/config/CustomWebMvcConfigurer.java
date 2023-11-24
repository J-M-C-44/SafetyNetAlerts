package com.safetynet.alerts.config;

import com.safetynet.alerts.controller.request.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// permet d'ajouter l'intercepteur de requête à l'application
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    private final RequestInterceptor requestInterceptor;
    public CustomWebMvcConfigurer(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor);
    }

}
