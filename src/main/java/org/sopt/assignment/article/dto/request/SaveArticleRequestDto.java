package org.sopt.assignment.article.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.sopt.assignment.article.domain.ETag;

public record SaveArticleRequestDto(
        @NotBlank
        @Schema(example = "커스텀 어노테이션에 대하여")
        String title,

        @NotBlank
        @Schema(example = "커스텀 어노테이션은 .....")
        String content,

        @NotBlank
        @Schema(example = "SPRING")
        ETag tag
) {
}
