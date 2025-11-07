package org.sopt.assignment.article.repository;

import org.sopt.assignment.article.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByMemberNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Article> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    boolean existsByTitle(String title);
}
