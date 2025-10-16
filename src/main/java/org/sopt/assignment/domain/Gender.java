package org.sopt.assignment.domain;

public enum Gender {
    MALE("남자"), FEMALE("여자");

    Gender(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
