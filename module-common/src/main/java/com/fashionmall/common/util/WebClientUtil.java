package com.fashionmall.common.util;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientUtil {

    private final WebClient webClient;

    private static final String CLIENT_ERROR_TEMPLATE = "Client error: Status code: {}, Body: {}, Headers: {}";
    private static final String SERVER_ERROR_TEMPLATE = "Server error: Status code: {}, Body: {}, Headers: {}";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T get(String uri, Class<T> responseType, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.get()
                .uri(uri, headers != null ? headers : Collections.emptyMap())
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(responseType)
                .block();
    }

    public <T> T get(String uri, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.get()
                .uri(uri, headers != null ? headers : Collections.emptyMap())
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T post(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.post()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(responseType)
                .block();
    }

    public <T> T post(String uri, Object requestBody, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.post()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T put(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.put()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(responseType)
                .block();
    }

    public <T> T patch(String uri, Object requestBody, Class<T> responseType, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.patch()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(responseType)
                .block();
    }

    public <T> void patch(String uri, Object requestBody, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        webClient.patch()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(elementTypeRef)
                .block();
    }

    public <T> T delete(String uri, Class<T> responseType, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.delete()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(responseType)
                .block();
    }

    public <T> T delete(String uri, ParameterizedTypeReference<T> elementTypeRef, Map<String, String> headers, ErrorResponseCode clientErrorCode, ErrorResponseCode serverErrorCode) {
        return webClient.delete()
                .uri(uri)
                .headers(getHttpHeadersConsumer(headers))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        handleClientError(response, clientErrorCode))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        handleServerError(response, serverErrorCode))
                .bodyToMono(elementTypeRef)
                .block();
    }

    private Consumer<HttpHeaders> getHttpHeadersConsumer(Map<String, String> headers) {
        return httpHeaders -> {
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpHeaders.add(
                            entry.getKey(),
                            entry.getValue()
                    );

                }
            }
        };
    }

    private Mono<Throwable> handleClientError(ClientResponse response, ErrorResponseCode errorCode) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log.warn(CLIENT_ERROR_TEMPLATE, response.statusCode().value(), body, response.headers().asHttpHeaders());
                    String message = extractMessageFromJson(body);
                    return Mono.error(new CustomException(errorCode, List.of(message), body));
                });
    }

    private Mono<Throwable> handleServerError(ClientResponse response, ErrorResponseCode errorCode) {
        return response.bodyToMono(String.class)
                .flatMap(body -> {
                    log.warn(SERVER_ERROR_TEMPLATE, response.statusCode().value(), body,
                            response.headers().asHttpHeaders());
                    String message = extractMessageFromJson(body);
                    return Mono.error(new CustomException(errorCode, List.of(message), body));
                });
    }

    private String extractMessageFromJson(String body) {
        try {
            JsonNode rootNode = objectMapper.readTree(body);
            return rootNode.path("message").asText();
        } catch (Exception e) {
            log.warn("Json Parsing Error: {}", body);
            return "";
        }
    }
}
