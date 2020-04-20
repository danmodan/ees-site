package br.com.ees.site.client.config;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class SendgridFeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor(@Value(value = "${SENDGRID_API_KEY}") String sendgridApiKey) {
        return rt -> {
            rt.header(CONTENT_TYPE, APPLICATION_JSON_VALUE);
            rt.header(ACCEPT, APPLICATION_JSON_VALUE);
            rt.header(AUTHORIZATION, "Bearer " + sendgridApiKey);
        };
    }

}