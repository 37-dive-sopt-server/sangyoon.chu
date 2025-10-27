package org.sopt.assignment.member.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.sopt.assignment.member.domain.Member;
import org.sopt.assignment.global.util.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileMemberRepository implements  MemberRepository {

    private final String FILE_PATH = "data/memberFile/member.json";
    private final Gson gson;

    private final Map<Long, Member> memberMap;
    private long maxId;

    public FileMemberRepository() {
        this.gson = new Gson();
        this.memberMap = new ConcurrentHashMap<>();

        ensureFileExists();
        loadDataFromFile();
    }

    private void ensureFileExists() {
        try{
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                writeToFile(new ArrayList<>());
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private void loadDataFromFile() {
        try{
            List<Member> memberList = readFromFile();
            memberMap.clear();

            for (Member member : memberList) {
                memberMap.put(member.getId(), member);

                if(member.getId() >= maxId){
                    maxId = member.getId() + 1;
                }
            }
            IdGenerator.syncMemberSequence(maxId);

        } catch(Exception e){
        }
    }

    private void writeToFile(List<Member> members) {
        try{
            String jsonContent = gson.toJson(members);
            Files.write(Paths.get(FILE_PATH), jsonContent.getBytes());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<Member> readFromFile() {
        try{
            String jsonContent = new String(Files.readAllBytes(Paths.get(FILE_PATH)));

            Type userListType = new TypeToken<List<Member>>(){}.getType();
            List<Member> members = gson.fromJson(jsonContent, userListType);

            return members != null ? members : new ArrayList<>();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void syncToFile(){
        List<Member> memberList = new ArrayList<>(memberMap.values());
        writeToFile(memberList);
    }


    @Override
    public Member save(Member member) {

        memberMap.put(member.getId(), member);

        syncToFile();

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = memberMap.get(id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(memberMap.values());
    }

    @Override
    public boolean existsMemberByEmail(String email) {
        return memberMap.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public String delete(Long memberId) {
        Member removedMember = memberMap.remove(memberId);

        syncToFile();

        return removedMember.getName();
        }
}
