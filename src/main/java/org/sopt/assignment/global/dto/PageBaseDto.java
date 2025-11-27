package org.sopt.assignment.global.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageBaseDto<T>(
        List<T> content,

        int currentPage,

        int totalPage,

        long totalElements,

        boolean hasNext
) {
    public static <T> PageBaseDto<T> from(Page<T> pageList){
        return new PageBaseDto<>(
                pageList.getContent(),
                pageList.getNumber(),
                pageList.getTotalPages(),
                pageList.getTotalElements(),
                pageList.hasNext()
        );
    }
}
