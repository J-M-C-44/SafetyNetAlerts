package com.safetynet.alerts.controller.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        logger.info("input request : {} {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return true;
    }

    @Override
    public void afterCompletion(@Nullable HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, @Nullable Exception ex) {
        logger.info("output response : {} {}", response.getStatus(), response.getContentType());
    }
}
