package org.sopt.assignment.Handler;

import org.sopt.assignment.domain.Member;

import java.util.List;

public class OutputHandler {

    public void displayMenu(){
        System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
        System.out.println("---------------------------------");
        System.out.println("1️⃣. 회원 등록 ➕");
        System.out.println("2️⃣. ID로 회원 조회 🔍");
        System.out.println("3️⃣. 전체 회원 조회 📋");
        System.out.println("4️⃣. 회원 삭제 ❌");
        System.out.println("5️⃣. 종료 🚪");
        System.out.println("---------------------------------");
    }

    public void displayMember(Member member) {
        System.out.println("✅ 조회된 회원: " + formatMember(member));
    }

    public void displayAllMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("ℹ️ 등록된 회원이 없습니다.");
            return;
        }

        System.out.println("--- 📋 전체 회원 목록 📋 ---");
        members.forEach(member ->
                System.out.println("👤 " + formatMember(member))
        );
        System.out.println("--------------------------");
    }

    public void displaySuccess(String message){
        System.out.println("✅ Success: " + message);
    }

    public void displayMemberRegistered(Long memberId) {
        displaySuccess("회원 등록 완료 (ID: " + memberId + ")");
    }

    public void displayMemberDeleted(String memberName) {
        displaySuccess(memberName + "님의 회원 정보가 삭제되었습니다.");
    }

    public void displayQuitMenu(){
        System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
    }

    private String formatMember(Member member) {
        return "ID: " + member.getId() +
                " | 이름: " + member.getName() +
                " | 📧: " + member.getEmail() +
                " | 🎂: " + member.getBirthday() +
                " | 👥: " + member.getGender().getDescription();
    }
}
