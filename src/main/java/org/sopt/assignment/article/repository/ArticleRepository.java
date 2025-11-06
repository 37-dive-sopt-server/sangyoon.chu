package org.sopt.assignment.article.repository;

import org.sopt.assignment.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByMemberId(Long memberId);
    Optional<Article> findByIdAndMemberId(Long id, Long memberId);

    boolean existsByTitle(String title);
}
