package com.lvtn.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "user-not-found"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "wrong-password"),
    USER_ALREADY_EXIST(400, "username-already-exist"),
    TOKEN_INVALID(HttpStatus.FORBIDDEN.value(), "token-invalid-or-revoked"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "token-expired"),
    DIRECTION_NOT_CORRECT(HttpStatus.BAD_REQUEST.value(), "direction-not-found"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "unauthenticated"),
    NEW_PASSWORD_EQUALS_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST.value(), "new-password-equals-current-password"),
    CONFIRM_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST.value(), "confirm-password-not-match"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "forbidden"),
    ERROR(HttpStatus.FORBIDDEN.value(), "error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "bad request"),
    BRAND_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "brand-not-found"),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "category-not-found"),
    INVENTORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "inventory-not-found"),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST.value(), "out-of-stock"),
    CREATED_PAYMENT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "created-payment-failed"),
    ;

    private final int code;
    private final String message;
}
