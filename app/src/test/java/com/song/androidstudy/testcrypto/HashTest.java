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
}
