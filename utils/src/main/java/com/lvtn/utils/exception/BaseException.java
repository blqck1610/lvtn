package com.lvtn.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException  {
    @Serial
    private static final long serialVersionUID = 1L;
    private HttpStatus code;
    private String message;



}
