package com.lvtn.utils.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_DELETE
            )
    );
    private final Set<Permission> permissions;

//    public List<SimpleGrantedAuthority> getAuthorities(){
//        var authorities = getPermissions().stream()
//                .map( permissions -> new SimpleGrantedAuthority(permissions.getPermission()))
//                .collect(Collectors.toList());
//
//        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
//        return authorities;


}
