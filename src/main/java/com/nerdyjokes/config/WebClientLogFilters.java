package com.nerdyjokes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientLogFilters {

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n")
                    .append(clientRequest.method())
                    .append(" ")
                    .append(clientRequest.url());
                clientRequest
                    .headers()
                    .forEach((name, values) -> values.forEach(value -> sb
                        .append("\n")
                        .append(name)
                        .append(":")
                        .append(value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Response: \n")
                    .append("Status: ")
                    .append(clientResponse.rawStatusCode());
                clientResponse
                    .headers()
                    .asHttpHeaders()
                    .forEach((key, value1) -> value1.forEach(value -> sb
                        .append("\n")
                        .append(key)
                        .append(":")
                        .append(value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientResponse);
        });
    }
}