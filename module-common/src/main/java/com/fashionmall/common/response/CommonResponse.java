package com.fashionmall.common.response;

import lombok.Getter;

@Getter
public class CommonResponse<T> {

    private final Integer status;
    private final String message;
    private final T data;

    public CommonResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public CommonResponse() {
        this.status = null;
        this.message = null;
        this.data = null;
    }
}
