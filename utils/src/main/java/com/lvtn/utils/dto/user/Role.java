package com.lvtn.utils.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER,
    ADMIN,
    MANAGER,
    SUPERVISOR
}
