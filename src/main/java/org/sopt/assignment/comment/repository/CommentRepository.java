package org.sopt.assignment.comment.repository;

import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.query.CommentQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.member WHERE c.article.id = :articleId")
    Page<Comment> findByArticleId(Long articleId, Pageable pageable);

    Page<Comment> findCommentByArticleId(Long articleId, Pageable pageable);

    @Query("""
    SELECT new org.sopt.assignment.comment.dto.response.GetCommentResponseDto(
        c.id,
        c.member.name,
        c.createdAt,
        c.content,
        c.isUpdate
    )
    FROM Comment c
    WHERE c.article.id = :articleId
    """)
    Page<CommentQueryDto> findCommentDtoByArticleId(
            @Param("articleId") Long articleId,
            Pageable pageable
    );

    @Query("SELECT c.id as id, c.content as content, c.member.name as memberName, c.createdAt as createdAt, c.isUpdate as isUpdate " +
            "FROM Comment c WHERE c.article.id = :articleId")
    Page<CommentSummary> findCommentSummariesByArticleId(@Param("articleId") Long articleId, Pageable pageable);

    @Query(value = """
    SELECT 
        c.id,
        m.name,
        c.created_at,
        c.content,
        c.is_update
    FROM comments c
    JOIN members m ON c.member_id = m.id
    WHERE c.article_id = :articleId
    """, nativeQuery = true)
    Page<Object[]> findCommentsByArticleIdNative(
            @Param("articleId") Long articleId,
            Pageable pageable
    );
}
