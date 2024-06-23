package org.delivery.db.store.enums;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {
    //등록전, 승인 대기 등

    REGISTERED("등록"),

    UNREGISTERED("해지")

    ;

    private String description;
}
