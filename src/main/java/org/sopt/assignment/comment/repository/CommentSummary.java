package org.sopt.assignment.comment.repository;

import java.time.LocalDateTime;

public interface CommentSummary {
    Long getId();
    String getContent();
    String getMemberName();
    LocalDateTime getCreatedAt();
    boolean getIsUpdate();
}

