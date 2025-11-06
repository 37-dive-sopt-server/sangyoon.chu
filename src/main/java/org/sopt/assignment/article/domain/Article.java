package org.sopt.assignment.article.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.assignment.article.exception.ArticleErrorCode;
import org.sopt.assignment.global.base.BaseTimeEntity;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "articles")
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "tag", nullable = false)
    @Enumerated(EnumType.STRING)
    private ETag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder(access = AccessLevel.PRIVATE)
    private Article(
            final String title,
            final String content,
            final ETag tag,
            Member member){
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.member = member;
    }

    public static Article create(final String title, final String content, ETag tag, Member member) {
        validateCreation(title, content, tag);
        return Article.builder()
                .title(title)
                .content(content)
                .tag(tag)
                .member(member)
                .build();
    }

    private static void validateCreation(String title, String content, ETag tag){
        validateTitle(title);
        validateContent(content);
    }

    private static void validateTitle(String title){
        if(title == null || title.isEmpty()){
            throw BaseException.type(ArticleErrorCode.NOT_EMPTY_TITLE);
        }
    }
    private static void validateContent(String content){
        if(content == null || content.isEmpty()){
            throw BaseException.type(ArticleErrorCode.NOT_EMPTY_CONTENT);
        }
    }
}
