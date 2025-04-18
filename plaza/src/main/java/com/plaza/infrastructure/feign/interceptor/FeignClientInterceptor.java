package com.plaza.infrastructure.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                // Depurar las cookies enviadas
                Arrays.stream(cookies).forEach(cookie ->
                        System.out.println("Cookie enviada: " + cookie.getName() + "=" + cookie.getValue())
                );
                String cookieHeader = Arrays.stream(cookies)
                        .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                        .reduce((a, b) -> a + "; " + b)
                        .orElse("");
                template.header("Cookie", cookieHeader);
            }
        }
    }
}