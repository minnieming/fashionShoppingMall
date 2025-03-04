package com.fashionmall.common.util;

import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.response.CommonResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseUtil {

    private static final Integer successStatus = HttpStatus.OK.value();
    private static final String successMessage = "성공";

    public static <T> CommonResponse<T> success(){
        return new CommonResponse<>(successStatus, successMessage, null);
    }

    public static <T>CommonResponse<T> success(T data){
        return new CommonResponse<>(successStatus, successMessage, data);
    }

    public static <T>CommonResponse<T> failure(ErrorResponseCode errorCode) {
        return new CommonResponse<>(errorCode.getStatus().value(), errorCode.getMessage());
    }

    public static <T>CommonResponse<T> failure(Integer statusCode, String message) {
        return new CommonResponse<>(statusCode, message);
    }
}
