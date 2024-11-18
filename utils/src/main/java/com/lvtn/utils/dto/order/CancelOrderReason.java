package com.lvtn.utils.dto.order;

import lombok.Getter;

@Getter
public enum CancelOrderReason {
    TEST("test");

    private final String message;

    CancelOrderReason(String message) {
        this.message = message;
    }
}
