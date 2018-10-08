package com.song.androidstudy.testcrypto;

import com.song.androidstudy.crypto.Base64;
import com.song.androidstudy.crypto.RSAUtils;

import org.junit.Test;

public class RSACryptoTest {

    @Test
    public void test_rsa_crypto() throws Exception {

        System.out.println(RSAUtils.RSA_PRIVATE_KEY.length()*3/4);
        System.out.println(RSAUtils.RSA_PUBLIC_KEY.length()*3/4);

//        byte[] bytes_private = RSAUtils.encrypt("test".getBytes(), RSAUtils.getPrivateKey(RSAUtils.RSA_PRIVATE_KEY));
//        System.out.println("bytes_private-->" + bytes_private.length);
//
//        byte[] bytes_public = RSAUtils.decrypt(bytes_private, RSAUtils.getPublicKey(RSAUtils.RSA_PUBLIC_KEY));
//        System.out.println(new String(bytes_public));

        String str = "testtesttesttesttssssssssssssssssssssssssesttesttesttessttesttesttsttesttessttesttesttesttesttesttestteesttesttesttesttesttesttesttesttesttest";
        System.out.println("str-->" + str.length());

        byte[] encrypt = RSAUtils.encrypt(str.getBytes(), RSAUtils.getPublicKey(RSAUtils.RSA_PUBLIC_KEY));
        System.out.println(encrypt.length);
        System.out.println(encrypt);

        byte[] decrypt = RSAUtils.decrypt(encrypt, RSAUtils.getPrivateKey(RSAUtils.RSA_PRIVATE_KEY));
        System.out.println(decrypt.length);
        System.out.println(new String(decrypt));

        // 签名
        byte[] sign = RSAUtils.sign(str.getBytes(), RSAUtils.getPrivateKey(RSAUtils.RSA_PRIVATE_KEY));
        System.out.println(Base64.encode(sign));

        // 验签
        boolean verify = RSAUtils.verify(str.getBytes(), sign, RSAUtils.getPublicKey(RSAUtils.RSA_PUBLIC_KEY));
        System.out.println(verify);

    }
}
