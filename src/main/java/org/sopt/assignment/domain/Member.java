package org.sopt.assignment.domain;

import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    private Gender gender;

    public Member(Long id, String name, String email, LocalDate birthday, Gender gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
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