package edu.whu.week8.util;

import java.util.UUID;

public class IdGenerator {

    public static String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
