package com.javathinked.reflection.model.enums;

public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE"),
    NOT_DEFINED("NOT_DEFINE");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
