package org.sopt.assignment.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.service.ArticleService;
import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.command.CreateCommentCommandDto;
import org.sopt.assignment.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public void createComment(CreateCommentCommandDto command){


        Article article = articleService.get(command.articleId());

        Comment.create(command.comment())
    }
}
