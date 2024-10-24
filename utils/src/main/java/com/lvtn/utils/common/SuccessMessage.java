package com.lvtn.utils.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    CREATED_SUCCESS("created-successfully"),
    UPDATED_SUCCESS("updated-successfully"),
    GET_SUCCESS("get-data-successful"),
    DELETE_SUCCESS("delete-data-successful"),
    AUTHENTICATE_SUCCESS("authenticate-success"),
    OK("ok"),
    ;
    private final String message;

}
