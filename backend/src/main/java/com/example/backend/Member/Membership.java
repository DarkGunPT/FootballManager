package com.example.backend.Member;

public enum Membership {
    VIP("VIP"),
    ADMIN("ADMIN"),
    PLAYER("PLAYER"),
    COACH("COACH"),
    BOARD("BOARD"),
    PRESIDENT("PRESIDENT");
    private final String description;

    Membership(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
