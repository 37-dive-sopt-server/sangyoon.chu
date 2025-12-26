package org.sopt.assignment.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequestDto(
        @NotBlank
        @Schema(example = "약간 이해가 안되는 부분이 있어요")
        String comment
) {
}
