package org.delivery.db.storeuser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserRole {

    MASTER("마스터"),

    ADMIN("관리자"),

    USER("일반 사용자"),
    ;

    private String description;
}
