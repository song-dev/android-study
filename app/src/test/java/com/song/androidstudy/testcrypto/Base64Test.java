package com.song.androidstudy.testcrypto;

import org.junit.Test;

import java.util.Base64;

public class Base64Test {

    @Test
    public void test_base64(){
        String encodeToString = Base64.getEncoder().encodeToString("test".getBytes());
        System.out.println(encodeToString);
        byte[] decode = Base64.getDecoder().decode(encodeToString);
        System.out.println(new String(decode));
    }

    @Test
    public void test_custom_base64(){
        String encode = com.song.androidstudy.crypto.Base64.encode("test".getBytes());
        System.out.println(encode);
        byte[] decode = com.song.androidstudy.crypto.Base64.decode(encode);
        System.out.println(new String(decode));
    }
}
