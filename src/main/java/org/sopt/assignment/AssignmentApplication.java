package org.sopt.assignment;

import org.sopt.assignment.controller.MemberController;
import org.sopt.assignment.domain.Gender;
import org.sopt.assignment.domain.Member;
import org.sopt.assignment.repository.MemoryMemberRepository;
import org.sopt.assignment.service.MemberServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        MemberServiceImpl memberService = new MemberServiceImpl();
        MemberController memberController = new MemberController();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원 삭제");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("⚠️ 이름을 입력해주세요.");
                        continue;
                    }
                    System.out.println("등록할 회원 이메일을 입력하세요: ");
                    String email = scanner.nextLine();
                    if(email.trim().isEmpty()){
                        System.out.println("⚠️ 이메일을 입력해주세요.");
                        continue;
                    }
                    if(memberController.existsMemberByEmail(email)){
                        System.out.println("⚠️ 중복된 이메일 입니다. 다른 이메일을 입력해주세요.");
                        continue;
                    }
                    System.out.println("등록할 회원 생일을 입력해주세요(형식: yyyy-MM-dd)");
                    String userBirthday = scanner.nextLine();
                    LocalDate birthday;
                    try {
                        birthday = LocalDate.parse(userBirthday);
                    } catch (DateTimeParseException e) {
                        System.out.println("❌ 잘못된 날짜 형식입니다. yyyy-MM-dd 형식으로 입력해주세요.");
                        continue;
                    }
                    System.out.println("등록할 성별을 입력해주세요 (남성은 1번 여성은 2번 입니다.)");
                    String userGender = scanner.nextLine();
                    Gender gender;
                    switch (userGender) {
                        case "1":
                            gender = Gender.MALE;
                            break;
                        case "2":
                            gender = Gender.FEMALE;
                            break;
                        default :
                            System.out.println("⚠️ 성별은 1 또는 2로만 선택해주세요.");
                            continue;
                    }

                    Long createdId = memberController.createMember(name, email, birthday, gender);
                    if (createdId != null) {
                        System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                    } else {
                        System.out.println("❌ 회원 등록 실패");
                    }
                    break;
                case "2":
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            System.out.println("✅ 조회된 회원: ID: " + foundMember.get().getId() +
                                    " | 이름: " + foundMember.get().getName() +
                                    " | 📧 Email: " + foundMember.get().getEmail() +
                                    " | 🎂 Birthday: " + foundMember.get().getBirthday() +
                                    " | 👥 Gender: " + foundMember.get().getGender().getDescription());}
                        else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;
                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    }
                    else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            System.out.println("👤 ID: " + member.getId() +
                                    " | 이름: " + member.getName() +
                                    " | 📧 Email: " + member.getEmail() +
                                    " | 🎂 Birthday: " + member.getBirthday() +
                                    " | 👥 Gender: " + member.getGender().getDescription());
                        }
                        System.out.println("--------------------------");
                    }
                    break;
                case "4":
                    System.out.print("삭제할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            String deleteName = memberController.deleteMember(id);
                            System.out.println("✅ " + deleteName + "  회원을 삭제했습니다.");
                        }
                        else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
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
}
