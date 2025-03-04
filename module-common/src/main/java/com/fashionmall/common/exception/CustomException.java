package com.fashionmall.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorResponseCode code;
    private List<String> messages;
    private String responseBody;

    public CustomException(ErrorResponseCode code) {
        this.code = code;
    }

    public CustomException(ErrorResponseCode code, List<String> messages, String responseBody) {
        this.code = code;
        this.messages = messages;
        this.responseBody = responseBody;
    }
}
