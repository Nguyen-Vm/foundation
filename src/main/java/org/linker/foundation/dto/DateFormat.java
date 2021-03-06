package org.linker.foundation.dto;

public enum DateFormat {
    NumDate("yyyyMMdd"),

    StrikeDate("yyyy-MM-dd"),

    NumDateTime("yyyyMMddHHmmss"),

    StrikeDateTime("yyyy-MM-dd HH:mm:ss");

    public String pattern;

    DateFormat(String pattern) {
        this.pattern = pattern;
    }
}
