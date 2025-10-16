package org.sopt.assignment.domain;

import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ErrorCode;

import java.time.LocalDate;
import java.time.Period;

public class Member {
    private static final int MINIMUM_AGE = 20;

    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate birthday;
    private final Gender gender;

    private Member(Long id, String name, String email, LocalDate birthday, Gender gender) {
        validateAge(birthday);

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
        validateAge(birthday);
    }

    private static void validateAge(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now())) {
            throw BaseException.type(ErrorCode.NOT_ALLOWED_FUTURE_BIRTHDAY);
        }

        int age = calculateAge(birthday);
        if (age < MINIMUM_AGE) {
            throw BaseException.type(ErrorCode.NOT_ALLOWED_AGE_UNDER_TWENTY);
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