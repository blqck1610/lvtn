package com.lvtn.utils.exception;

import com.lvtn.utils.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private int code;
    private String message;
}
