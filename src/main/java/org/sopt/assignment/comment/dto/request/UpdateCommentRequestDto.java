package org.sopt.assignment.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequestDto(
        @NotBlank
        @Schema(example = "흠 흥미로운데요")
        String comment
) {
}
