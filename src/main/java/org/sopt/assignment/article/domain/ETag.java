package org.sopt.assignment.article.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum ETag {
    CS("CS 지식"),
    DB("데이터베이스"),
    SPRING("스프링"),
    ETC("기타");

    ETag(String description){
        this.description = description;
    }

    private final String description;
}
