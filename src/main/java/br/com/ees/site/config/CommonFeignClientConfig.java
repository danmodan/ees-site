package br.com.ees.site.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import br.com.ees.site.client.config.FeignClientConfig;

@Configuration
@EnableFeignClients(
        basePackages = { "br.com.ees.site.client" },
        defaultConfiguration = { FeignClientConfig.class })
public class CommonFeignClientConfig { }