package org.sopt.assignment;

import org.sopt.assignment.controller.MemberController;
import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.exception.BaseException;
import org.sopt.assignment.exception.ExceptionHandler;
import org.sopt.assignment.repository.MemoryMemberRepository;
import org.sopt.assignment.service.MemberServiceImpl;
import org.sopt.assignment.validator.MemberInputValidator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {

        MemoryMemberRepository repository = new MemoryMemberRepository();
        MemberServiceImpl memberService = new MemberServiceImpl(repository);
        MemberController memberController = new MemberController(memberService);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원 삭제 ❌");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerMember(scanner, memberController);
                    break;
                case "2":
                    findMemberById(scanner, memberController);
                    break;
                case "3":
                    displayAllMembers(memberController);
                    break;
                case "4":
                    deleteMember(scanner, memberController);
                    break;
                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private static void registerMember(Scanner scanner, MemberController memberController) {
        try {
            System.out.print("등록할 회원 이름을 입력하세요: ");
            String name = scanner.nextLine();
            MemberInputValidator.validateName(name);

            System.out.print("등록할 회원 이메일을 입력하세요: ");
            String email = scanner.nextLine();
            MemberInputValidator.validateEmail(email);

            System.out.print("등록할 회원 생일을 입력해주세요(형식: yyyy-MM-dd): ");
            String userBirthday = scanner.nextLine();
            LocalDate birthday = LocalDate.parse(userBirthday);
            MemberInputValidator.validateBirthday(birthday);

            System.out.println("등록할 성별을 입력해주세요 (남성은 1번 여성은 2번 입니다.)");
            String userGender = scanner.nextLine();
            Gender gender = Gender.fromInput(userGender);

            Long createdId = memberController.createMember(name, email, birthday, gender);
            System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");

        } catch (BaseException | DateTimeParseException e) {
            ExceptionHandler.handle(e);
        }
    }

    private static void findMemberById(Scanner scanner, MemberController memberController) {
        System.out.print("조회할 회원 ID를 입력하세요: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            var member = memberController.findMemberById(id);
            System.out.println("✅ 조회된 회원: ID: " + member.getId() +
                    " | 이름: " + member.getName() +
                    " | 📧: " + member.getEmail() +
                    " | 🎂: " + member.getBirthday() +
                    " | 👥: " + member.getGender().getDescription());
            } catch (BaseException | NumberFormatException e) {
                ExceptionHandler.handle(e);
            }
    }

    private static void displayAllMembers(MemberController memberController) {
        var allMembers = memberController.getAllMembers();
        if (allMembers.isEmpty()) {
            System.out.println("ℹ️ 등록된 회원이 없습니다.");
        } else {
            System.out.println("--- 📋 전체 회원 목록 📋 ---");
            for (var member : allMembers) {
                System.out.println("👤 ID: " + member.getId() +
                        " | 이름: " + member.getName() +
                        " | 📧: " + member.getEmail() +
                        " | 🎂: " + member.getBirthday() +
                        " | 👥: " + member.getGender().getDescription());
            }
            System.out.println("--------------------------");
        }
    }

    private static void deleteMember(Scanner scanner, MemberController memberController) {
        System.out.print("삭제할 회원 ID를 입력하세요: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            String deletedName = memberController.deleteMember(id);
            System.out.println("✅ " + deletedName + "님의 회원 정보가 삭제되었습니다.");
        } catch (NumberFormatException | BaseException e) {
            ExceptionHandler.handle(e);
        }
    }
}