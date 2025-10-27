package org.sopt.assignment.global.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private IdGenerator(){}

    private static final AtomicLong memberSequence = new AtomicLong(1L);

    public static void syncMemberSequence(Long maxId) {
        if (maxId != null && maxId >= memberSequence.get()) {
            memberSequence.set(maxId + 1);
        }
    }
    public static Long generateMemberId() {
        return memberSequence.getAndIncrement();
    }
}
