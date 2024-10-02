package com.lvtn.utils.exception.handler;



import com.lvtn.utils.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handlerBaseException(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handlerBindException(BindException e) {
        String errorMessage = "invalid request!";
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        }

        return ResponseEntity.badRequest().body(errorMessage );

    }
}
