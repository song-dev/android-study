package com.song.androidstudy.testcrypto;

import com.song.androidstudy.BuildConfig;
import com.song.androidstudy.crypto.AESUtils;
import com.song.androidstudy.crypto.Base64;
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
        String content = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttsttest";
        System.out.println("明文长度-->" + content.length());
        System.out.println("明文-->" + content);
        byte[] encrypt = AESUtils.encrypt(content, aesKey);
        String ciphertext = Base64.encode(encrypt);
        System.out.println("密文长度-->" + ciphertext.length());
        System.out.println("ciphertext-->" + ciphertext);

        String decrypt = AESUtils.decrypt(Base64.decode(ciphertext), aesKey);
        System.out.println("解密明文-->" + decrypt);
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

    /**
     * 另外一种的加密和解密
     *
     * @throws Exception
     */
    @Test
    public void test_aes_other() throws Exception {

        String aesKey = "0123456789abcdef";
        String content = "testtesttesttesttesttesttesttesttesttesttesttesttesttesttestest";
        System.out.println("明文长度-->" + content.length());
        System.out.println("明文-->" + content);
        byte[] encrypto = AESUtils.encrypto(content, aesKey);
        String ciphertext = HexBinDecOctUtils.bytesToHex(encrypto);
        System.out.println("密文长度-->" + ciphertext.length() / 2);
        System.out.println("ciphertext-->" + ciphertext);

        // 解密
        byte[] decrypto = AESUtils.decrypto(encrypto, aesKey);
        System.out.println();
        System.out.println("解密明文-->" + new String(decrypto));


    }



}
