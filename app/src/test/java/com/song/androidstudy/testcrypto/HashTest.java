package com.song.androidstudy.testcrypto;

import com.song.androidstudy.crypto.HashUtils;

import org.junit.Test;

public class HashTest {

    @Test
    public void test_md5(){
        String test = HashUtils.md5Encode("test");
        System.out.println(test.length());
        System.out.println(test);
    }

    @Test
    public void test_sha1(){
        String test = HashUtils.sha1Encode("test");
        System.out.println(test.length());
        System.out.println(test);
    }

    @Test
    public void test_sha256(){
        String test = HashUtils.sha256Encode("test");
        System.out.println(test.length());
        System.out.println(test);
    }

    @Test
    public void test_hmacsha1(){
        String test = HashUtils.hmacsha1Encode("test","e6OXZ8q2cdJQctncOhd4qyT8iR32jZwU");
        System.out.println(test.length());
        System.out.println(test);
    }
}
