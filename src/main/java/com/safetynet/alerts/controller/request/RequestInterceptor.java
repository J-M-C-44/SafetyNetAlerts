package com.safetynet.alerts.controller.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("input request : {} {} {}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return true;
    }

    // TODO : à revoir car si exception, on ne passe pas dans postHandle --> on ne doit probablement pas passer par le controller en retour d'exception
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//        logger.info("output response : {} {}", response.getStatus(), response.getContentType());
//    }

    // TODO : comment récupérer le body ?
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("output response : {} {}", response.getStatus(), response.getContentType());
    }
}
