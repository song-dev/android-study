package com.song.androidstudy.testcrypto;

import com.song.androidstudy.BuildConfig;
import com.song.androidstudy.crypto.AESUtils;
import com.song.androidstudy.crypto.HexBinDecOctUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AESCryptoTest {

    /**
     * AES加密测试
     */
    @Test
    public void test_aes128Encryption() {
        String aesKey = "0123456789abcdef";
        String content = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttest";
        System.out.println("明文长度-->" + content.length());
        byte[] decrypt = AESUtils.encrypt(content, aesKey);
        String ciphertext = HexBinDecOctUtils.bytesToHex(decrypt);
        System.out.println("密文长度-->" + ciphertext.length() / 2);
        System.out.println("ciphertext-->" + ciphertext);
    }

    /**
     * AES加密测试, jre默认不支持192和256位加密
     */
    @Test
    public void test_aes256Encryption() {
        String aesKey = "0123456789abcdef0123456789abcdef";
        System.out.println("AES key长度-->" + aesKey.length());
        String content = "test";
        System.out.println("明文长度-->" + content.length());
        byte[] decrypt = AESUtils.encrypt(content, aesKey);
        String ciphertext = HexBinDecOctUtils.bytesToHex(decrypt);
        System.out.println("密文长度-->" + ciphertext.length() / 2);
        System.out.println("ciphertext-->" + ciphertext);
    }

    @Test
    public void test_length() {
        String str = "219b586968eb0a65e4b874539bf1d1894edee624c57c430fd39ea63992331c79";
        System.out.println(str.length());
    }
}
