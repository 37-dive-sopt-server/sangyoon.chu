package org.sopt.assignment.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.assignment.global.base.BaseTimeEntity;
import org.sopt.assignment.global.constants.Constants;
import org.sopt.assignment.global.exception.BaseException;
import org.sopt.assignment.member.exception.MemberErrorCode;

import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
public class Member extends BaseTimeEntity {
    private static final int MINIMUM_AGE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private EGender gender;

    @Column(name = "role", nullable = false)
    @Enumerated
    private ERole role;

    @Builder(access = AccessLevel.PRIVATE)
    private Member (final String name,
                   final String email,
                   final LocalDate birthday,
                   final EGender gender,
                    final ERole role
                   ){
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }


    public static Member create(String name, String email, LocalDate birthday, EGender gender){
        validateCreation(name, email, birthday, gender);
        return Member.builder()
                .name(name)
                .email(email)
                .birthday(birthday)
                .gender(gender)
                .role(ERole.USER)
                .build();
    }

    public static void validateCreation(String name, String email, LocalDate birthday, EGender gender) {
        validateName(name);
        validateEmail(email);
        validateBirthday(birthday);
    }

    private static void validateBirthday(LocalDate birthday) {
        if (birthday == null) {
            throw BaseException.type(MemberErrorCode.NOT_EMPTY_BIRTHDAY);
        }

        if (birthday.isAfter(LocalDate.now())) {
            throw BaseException.type(MemberErrorCode.NOT_ALLOWED_FUTURE_BIRTHDAY);
        }

        int age = calculateAge(birthday);
        if (age < MINIMUM_AGE) {
            throw BaseException.type(MemberErrorCode.NOT_ALLOWED_AGE_UNDER_TWENTY);
        }
    }

    private static void validateName(String name){
        if (name == null || name.isBlank()) {
            throw BaseException.type(MemberErrorCode.NOT_EMPTY_NAME);
        }
    }

    private static void validateEmail(String email){
        if (email == null || email.isBlank()) {
            throw BaseException.type(MemberErrorCode.NOT_EMPTY_EMAIL);
        }

        if (!Constants.EMAIL_PATTERN.matcher(email).matches()) {
            throw BaseException.type(MemberErrorCode.INVALID_EMAIL);
        }
    }

    private static int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}