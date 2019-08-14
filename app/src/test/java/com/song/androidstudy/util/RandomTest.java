package com.song.androidstudy.util;

import org.junit.Test;

import java.security.SecureRandom;

/**
 * 随机生成 key
 * Created by chensongsong on 2019/8/14.
 */
public class RandomTest {

    @Test
    public void testRandom() {

        String bf_key = makeKey(32);
        System.out.println("bf_key: " + bf_key + "  bf_key length: " + bf_key.length());
        String aes_iv = makeKey(16);
        System.out.println("aes_iv: " + aes_iv + "  aes_iv length: " + aes_iv.length());

    }

    private String makeKey(int length) {

        String set = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        SecureRandom random = new SecureRandom();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(set.length());
            char c = set.charAt(index);
            buffer.append(c);
        }

        return buffer.toString();

    }

    @Test
    public void makeCharSet() {
        char s = 'A';
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 26; i++) {
            buffer.append((char) (s + i));
        }
        System.out.println(buffer.toString());
        System.out.println(buffer.length());
    }
}
