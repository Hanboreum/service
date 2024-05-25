package org.delivery.db.storeuser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserStatus {

    REGISTERED("등록"),

    UNREGISTRERED("해지"),
    ;

    private String description;
}
