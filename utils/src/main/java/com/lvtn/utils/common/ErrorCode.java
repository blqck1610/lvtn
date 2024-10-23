package com.lvtn.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(400, "user-not-found", HttpStatus.BAD_REQUEST),
    WRONG_PASSWORD(400, "wrong-password", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXIST(400, "user-already-exist", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID(403, "token-invalid", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(401, "token-expired", HttpStatus.UNAUTHORIZED)
    ;
    private final int code;
    private final String message;
    private final HttpStatus status;
}
