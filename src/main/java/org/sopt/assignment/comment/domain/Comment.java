package org.sopt.assignment.comment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.global.base.BaseTimeEntity;
import org.sopt.assignment.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "comments")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    private Comment(final String content,
                    final Member member,
                    final Article article) {
        this.content = content;
        this.member = member;
        this.article = article;
    }

    public static Comment create(final String content,
                                 final Member member,
                                 final Article article) {
        return Comment.builder()
                .content(content)
                .member(member)
                .article(article)
                .build();
    }

}
