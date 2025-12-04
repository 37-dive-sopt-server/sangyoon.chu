package org.sopt.assignment.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.assignment.auth.dto.command.LoginCommandDto;
import org.sopt.assignment.auth.dto.request.JoinCommandDto;
import org.sopt.assignment.auth.dto.request.LoginRequestDto;
import org.sopt.assignment.auth.service.AuthService;
import org.sopt.assignment.global.annotation.LoginUser;
import org.sopt.assignment.global.dto.JwtDto;
import org.sopt.assignment.auth.dto.request.JoinRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/members")
    public void createMember(@RequestBody JoinRequestDto request) {
        authService.join(JoinCommandDto.from(request));
    }

    @PostMapping("/login")
    public JwtDto join(
            @RequestBody @Valid LoginRequestDto request
            ){
        return  authService.login(LoginCommandDto.from(request));
    }

    @PostMapping("/logout")
    public void logout(
        @LoginUser Long userId
    ){
        authService.logout(userId);
    }
}
