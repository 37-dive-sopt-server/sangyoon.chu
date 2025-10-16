package org.sopt.assignment.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private IdGenerator(){}

    private static final AtomicLong memberSequence = new AtomicLong(1L);

    public static Long generateMemberId() {
        return memberSequence.getAndIncrement();
    }
}
