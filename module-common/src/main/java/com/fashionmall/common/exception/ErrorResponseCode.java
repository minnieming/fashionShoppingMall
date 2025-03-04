package com.fashionmall.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResponseCode {

    //공통 코드
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "필수 값을 입력해 주세요"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),
    OUT_OF_STOCK(HttpStatus.CONFLICT, "재고가 부족합니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청을 찾을 수 없습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    //커스텀 코드
    ORDER_NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "유효하지 않은 쿠폰입니다"),
    ORDER_NOT_FOUND_BILLING_KEY(HttpStatus.NOT_FOUND, "유효하지 않은 결제 수단 입니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일 입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임 입니다."),
    DUPLICATE_COUPON_NAME(HttpStatus.CONFLICT, "이미 존재하는 쿠폰명 입니다."),
    JWT_NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "유효하지 않은 토큰입니다."),

    WRONG_ID(HttpStatus.BAD_REQUEST, "아이디를 다시 확인해주세요"),

    WRONG_USER_ID(HttpStatus.BAD_REQUEST, "userId를 다시 확인해주세요"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 동일하지 않습니다"),
    WRONG_USER_NAME(HttpStatus.BAD_REQUEST, "UserName에 맞는 유저가 존재하지 않습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 회원입니다."),

    // Item
    WRONG_ITEM_ID(HttpStatus.BAD_REQUEST, "상품 관련 ID를 다시 확인해주세요"),
    WRONG_ITEM_DETAIL_ID(HttpStatus.BAD_REQUEST, "item detail 아이디를 다시 확인해주세요"),
    WRONG_ITEM_DISCOUNT_ID(HttpStatus.BAD_REQUEST, "item discount 아이디를 다시 확인해주세요"),
    WRONG_CATEGORY_ID(HttpStatus.BAD_REQUEST, "카테고리 관련 ID를 다시 확인해주세요"),
    WRONG_RATE(HttpStatus.BAD_REQUEST, "정률(%)에 맞는 값을 입력해주세요."),
    WRONG_AMOUNT(HttpStatus.BAD_REQUEST, "정액(원)에 맞는 값을 입력해주세요."),
    BAD_DEDUCT_QUANTITY(HttpStatus.BAD_REQUEST, "재고 수량보다 더 큰 수량 입니다. 수량을 다시 확인해주세요"),
    BAD_RESTORE_QUANTITY(HttpStatus.BAD_REQUEST, "수령이 0보다 작습니다. 수량을 다시 확인해주세요"),
    DUPLICATE_DISCOUNT_STATUS(HttpStatus.BAD_REQUEST, "활성화된 할인 정보가 있습니다."),

    // Image
    NOT_FOUND_S3(HttpStatus.NOT_FOUND, "이미지가 S3에 존재하지 않습니다."),

    // cart
    DUPLICATE_CART_DETAIL_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 itemDetail Id 입니다."),
    WRONG_CART_ID(HttpStatus.BAD_REQUEST, "올바르지 않는 CartId 입니다."),

    // favorite
    DUPLICATE_TRUE(HttpStatus.CONFLICT, "이미 좋아요가 되어있습니다."),
    DUPLICATE_FALSE(HttpStatus.CONFLICT, "이미 좋아요가 취소 되어있습니다."),

    // webclient
    CLIENT_ERROR(HttpStatus.BAD_REQUEST, "클라이언트 요청 오류"),
    SERVER_ERROR_FROM_SERVICE(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스 서버 오류");

    private final HttpStatus status;
    private final String message;
}
