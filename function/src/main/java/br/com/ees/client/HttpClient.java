package br.com.ees.client;

import java.time.Duration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpClient {

    public static final java.net.http.HttpClient CLIENT = java.net.http.HttpClient
        .newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();

}
