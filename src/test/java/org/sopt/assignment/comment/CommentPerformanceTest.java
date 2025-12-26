package org.sopt.assignment.comment;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.assignment.article.domain.Article;
import org.sopt.assignment.article.domain.ETag;
import org.sopt.assignment.comment.domain.Comment;
import org.sopt.assignment.comment.dto.response.GetCommentResponseDto;
import org.sopt.assignment.comment.repository.CommentRepository;
import org.sopt.assignment.comment.service.CommentService;
import org.sopt.assignment.global.dto.PageBaseDto;
import org.sopt.assignment.member.domain.EGender;
import org.sopt.assignment.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class CommentPerformanceTest {



    @Autowired
    private EntityManager em;

    private Long articleId;

    @BeforeEach
    void setUp() {
        // Member 100명 생성
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Member member = Member.create(
                    "name" + i,
                    "email" + i + "@naver.com",
                    LocalDate.parse("2000-04-15"),
                    EGender.MALE,
                    "password" + i
            );
            em.persist(member);
            members.add(member);
        }

        // Article 1개 생성 (아무 Member나 사용)
        Article article = Article.create(
                "어노테이션에 대해",
                "어려워요",
                ETag.SPRING,
                members.get(0)
        );
        em.persist(article);
        articleId = article.getId();

        // 댓글 1000개 생성 (각 Member마다 10개씩)
        for (int i = 0; i < 10000; i++) {
            Member member = members.get(i / 10);  // 0-9: Member 0, 10-19: Member 1, ...
            Comment comment = Comment.create("테스트 댓글 " + i, member, article);
            em.persist(comment);

            // 100개마다 flush (메모리 절약)
            if (i % 100 == 0) {
                em.flush();
                em.clear();
            }
        }

        em.flush();
        em.clear();
    }

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("Fetch Join 만")
    void V1_성능_측정() {
        Pageable pageable = PageRequest.of(0, 1000);

        long startTime = System.currentTimeMillis();

        PageBaseDto<GetCommentResponseDto> result =
                commentService.getCommentsV1(1L, pageable);

        long endTime = System.currentTimeMillis();

        System.out.println("V1 실행 시간: " + (endTime - startTime) + "ms");
    }

    @Test
    @DisplayName("DTO Projection")
    void V2_성능_측정() {
        Pageable pageable = PageRequest.of(0, 1000);

        long startTime = System.currentTimeMillis();

        PageBaseDto<GetCommentResponseDto> result =
                commentService.getCommentsV2(1L, pageable);

        long endTime = System.currentTimeMillis();

        System.out.println("V2 실행 시간: " + (endTime - startTime) + "ms");
    }

    @Test
    @DisplayName("Interface projection")
    void V3_성능_측정() {
        Pageable pageable = PageRequest.of(0, 1000);

        long startTime = System.currentTimeMillis();

        PageBaseDto<GetCommentResponseDto> result =
                commentService.getCommentsV3(1L, pageable);

        long endTime = System.currentTimeMillis();

        System.out.println("V3 실행 시간: " + (endTime - startTime) + "ms");
    }

    @Test
    @DisplayName("Native Query")
    void V4_성능_측정() {
        Pageable pageable = PageRequest.of(0, 1000);

        long startTime = System.currentTimeMillis();

        PageBaseDto<GetCommentResponseDto> result =
                commentService.getCommentsV4(1L, pageable);

        long endTime = System.currentTimeMillis();

        System.out.println("V4 실행 시간: " + (endTime - startTime) + "ms");
    }

    @Test
    @DisplayName("N+1 문제")
    void V5_성능_측정() {
        Pageable pageable = PageRequest.of(0, 1000);

        long startTime = System.currentTimeMillis();

        PageBaseDto<GetCommentResponseDto> result =
                commentService.getCommentsV5(1L, pageable);

        long endTime = System.currentTimeMillis();

        System.out.println("V5 실행 시간: " + (endTime - startTime) + "ms");
    }

}
