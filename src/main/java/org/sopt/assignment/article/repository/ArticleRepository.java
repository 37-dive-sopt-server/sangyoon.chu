package org.sopt.assignment.article.repository;

import org.sopt.assignment.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN FETCH a.member")
    Page<Article> findAllWithMember(Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.member WHERE UPPER(a.title) LIKE UPPER(CONCAT('%', :keyword, '%'))")
    Page<Article> findByTitleContainingIgnoreCaseWithMember(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.member WHERE UPPER(a.member.name) LIKE UPPER(CONCAT('%', :keyword, '%'))")
    Page<Article> findByMemberNameContainingIgnoreCaseWithMember(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT a FROM Article a JOIN FETCH a.member WHERE a.id = :id")
    Optional<Article> findByIdWithMember(@Param("id") Long id);

    boolean existsByTitle(String title);
}
