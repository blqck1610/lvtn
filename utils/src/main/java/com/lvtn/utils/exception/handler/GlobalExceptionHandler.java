package com.lvtn.utils.exception.handler;



import com.lvtn.utils.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handlerBaseException(BaseException e) {
        System.out.println("exception: " + e.getMessage());
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handlerBindException(BindException e) {
        String errorMessage = "invalid request!";
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        }
        return ResponseEntity.status(400).body(errorMessage);

    }
}
