package com.group8.library_management.enums;

import lombok.Getter;

@Getter
public enum EntityType {
    BOOK("book"),
    USER("user"),
    AUTHOR("author"),
    PUBLISHER("publisher");

    private final String directory;

    EntityType(String directory) {
        this.directory = directory;
    }
}
