package com.lvtn.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private int code;
    private String message;

    public BaseException(BaseException e) {
        this.code = e.code;
        this.message = e.message;
    }
}
