package org.sopt.assignment.article.domain;

import lombok.Getter;

@Getter
public enum ESearchType {
    TITLE("제목"),
    NAME("작성자이름");

    ESearchType(String description){
        this.description = description;
    }
    private final String description;
}
