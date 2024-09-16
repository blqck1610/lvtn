package com.lvtn.utils.exception.handler;



import com.lvtn.utils.exception.BaseException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ApiResponse handlerBaseException(BaseException e) {
        System.out.println("exception: " + e.getMessage());
        return new ApiResponse(e.getCode(), e.getMessage(), null);
    }


    @ExceptionHandler(BindException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handlerBindException(BindException e) {
        String errorMessage = "invalid request!";
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        }
        return new ApiResponse(400, e.getMessage(), null);

    }
}
