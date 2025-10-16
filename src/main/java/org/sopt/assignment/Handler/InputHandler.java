package org.sopt.assignment.Handler;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readMenuChoice(){
        System.out.print("메뉴를 선택하세요: ");
        return scanner.nextLine();
    }

    public String readName(){
        System.out.print("등록할 회원 이름을 입력하세요: ");
        return scanner.nextLine();
    }

    public String readEmail(){
        System.out.print("등록할 회원 이메일을 입력하세요: ");
        return scanner.nextLine();
    }

    public String readBirthday(){
        System.out.print("등록할 회원 생일을 입력해주세요(형식: yyyy-MM-dd): ");
        return scanner.nextLine();
    }

    public String readGender(){
        System.out.println("등록할 성별을 입력해주세요 (남성은 1번 여성은 2번 입니다.)");
        return scanner.nextLine();
    }

    public String readMemberId(){
        System.out.print("해당하는 회원 ID를 입력하세요: ");
        return scanner.nextLine();
    }
}
