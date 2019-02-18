package com.zooms.dean.auth.common;

import java.util.Random;

public final class DeanUtils {

    public static String randomInt(int num) {
        if (num == 0) {
            num = 6;
        }
        Random random = new Random();
        char[] chars = new char[num];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ('0' + random.nextInt(10));
        }
        return String.valueOf(chars);
    }

}
