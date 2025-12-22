package org.sopt.assignment.comment.repository;

import org.sopt.assignment.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
