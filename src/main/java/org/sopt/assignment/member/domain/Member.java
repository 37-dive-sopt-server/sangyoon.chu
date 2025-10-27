package org.sopt.assignment.member.domain;

import org.sopt.assignment.global.util.exception.BaseException;
import org.sopt.assignment.member.exception.MemberErrorCode;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Member {
    private static final int MINIMUM_AGE = 20;

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate birthday;
    private final Gender gender;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private Member(Long id, String name, String email, LocalDate birthday, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
    }

    public static Member create(Long id, String name, String email, LocalDate birthday, Gender gender){
        return new Member(id, name, email, birthday, gender);
    }

    public static void validateCreation(String name, String email, LocalDate birthday, Gender gender) {
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
        if (name == null || name.isEmpty()) {
            throw BaseException.type(MemberErrorCode.NOT_EMPTY_NAME);
        }
    }

    private static void validateEmail(String email){
        if (email == null || email.isEmpty()) {
            throw BaseException.type(MemberErrorCode.NOT_EMPTY_EMAIL);
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw BaseException.type(MemberErrorCode.INVALID_EMAIL);
        }
    }

    private static int calculateAge(LocalDate birthday) {
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Gender getGender() {
        return gender;
    }
}