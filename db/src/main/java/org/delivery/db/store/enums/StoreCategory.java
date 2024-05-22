package org.delivery.db.store.enums;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {

    CHINEESS_FOOD("중식","중식"),

    WESTERN_FOOD("양식","양식"),

    KOREAN_FOOD("한식","한식"),

    JAPENESS_FOOD("일식","일식"),

    CHICKEN("치킨","치킨"),

    PIZZA("피자","피자"),

    HAMBURGER("햄버거","햄버거"),

    COFFEE_TEA("커피와 차","커피와 차")

    ;


    private String display;
    private String description;
}
