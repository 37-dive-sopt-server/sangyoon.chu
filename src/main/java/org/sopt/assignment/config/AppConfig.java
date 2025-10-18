package org.sopt.assignment.config;

import org.sopt.assignment.Handler.InputHandler;
import org.sopt.assignment.Handler.OutputHandler;
import org.sopt.assignment.controller.MemberController;
import org.sopt.assignment.repository.MemberRepository;
import org.sopt.assignment.repository.MemoryMemberRepository;
import org.sopt.assignment.service.MemberService;
import org.sopt.assignment.service.MemberServiceImpl;

import java.util.Scanner;

public class AppConfig {

    public MemberController memberController(){
        return new MemberController(memberService());
    }

    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }

    public Scanner scanner(){
        return new Scanner(System.in);
    }

    public InputHandler inputHandler(){
        return new InputHandler(scanner());
    }

    public OutputHandler outputHandler(){
        return new OutputHandler();
    }
}
