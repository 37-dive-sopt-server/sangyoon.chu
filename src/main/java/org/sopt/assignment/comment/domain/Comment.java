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

    @Column(name = "content", length = 300, nullable = false)
    private String content;

    @Column(name = "isUpdate", nullable = false)
    private boolean isUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "article_id",
            foreignKey = @ForeignKey(
                    name = "fk_comment_article",
                    foreignKeyDefinition = "FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE"
            )
    )private Article article;

    @Builder
    private Comment(final String content,
                    final Member member,
                    final Article article) {
        this.content = content;
        this.member = member;
        this.article = article;
        this.isUpdate = false;
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

    public boolean belongsToArticle(Long articleId) {
        return this.article.getId().equals(articleId);
    }

    public boolean isOwnedBy(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public void update(final String content){
        this.content = content;
        this.isUpdate = true;
    }
}
