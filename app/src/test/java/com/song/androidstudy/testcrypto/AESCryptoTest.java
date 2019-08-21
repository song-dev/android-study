package com.song.androidstudy.testcrypto;

import com.song.androidstudy.crypto.AESUtils;
import com.song.androidstudy.crypto.Base64;
import com.song.androidstudy.crypto.HexBinDecOctUtils;

import org.junit.Test;

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

    @Test
    public void test_baidu_device_id() {

        /**
         * .cuid2   WJH76da+BAjGK/W0K6RXRpRc8WAHtE9G/gl9qnoeFFGdJKTRIHP525dqbsV1KEm8gG72ABdYHmu0LoN6QDLuulCzRJTlvfQ8uB5zurqcGzI=
         * {"deviceid":"737CDFE919AF2E767C4DB86A393CBE7D","imei":"","ver":2}
         * .cuid    W+LPOTwt0oTPMp+gopRDJ1uNsVrBQKnb/7R2j6g+UhU4MpIk+ZW8v/eh9L1zSEUigJpL+CCokPuxPtnATkZeow==
         * 866373029381385=D628F0F3BCE89CB895F535D4BEB3C00A
         * .fdid    +yEgRDYTC2HW3X6aLzLJ88twt11nWR+ixDwLcCUEvn6g0JcYRlOQMzR3UxWTkbQr
         * .uuid    5D2D6AF76D75CACF2B40257C7E697BFA
         */

        String cuid1 = "W+LPOTwt0oTPMp+gopRDJ1uNsVrBQKnb/7R2j6g+UhU4MpIk+ZW8v/eh9L1zSEUigJpL+CCokPuxPtnATkZeow==";
        byte[] decrypto_cuid1 = AESUtils.decrypt(Base64.decode(cuid1), "30212102dicudiab", "30212102dicudiab".getBytes());
        System.out.println("cuid1 解密明文-->" + new String(decrypto_cuid1));

        String cuid2 = "WJH76da+BAjGK/W0K6RXRpRc8WAHtE9G/gl9qnoeFFGdJKTRIHP525dqbsV1KEm8gG72ABdYHmu0LoN6QDLuulCzRJTlvfQ8uB5zurqcGzI=";
        byte[] decrypto_cuid2 = AESUtils.decrypt(Base64.decode(cuid2), "30212102dicudiab", "30212102dicudiab".getBytes());
        System.out.println("cuid2 解密明文-->" + new String(decrypto_cuid2));

        String ss = "2dbe2877d7acaacbc25a67042a27cad9b6c1cfabfd4ce42678b3d937716bdffa6abe82cdc0cab313b0a3b9d1e56547beae4e4caa3f82c6a32337bef073554573b8df85ec6fa3aab0cfadc72943422ac9b2ea8af53a98cf8dcacfb329446d4ebb43d03d9039e3ed22fa19b0c6729664f4be8a60fed67c34fcfa17cd677a9 9820cfb858f324ce2225572c7aca72cd79f7c9d7cdb5c20885d676aac8ee375 b9e550a57ab10ebcb454aede1566b386a0d0a3ea9a732aa9a0d1d76c853a 18b117d474be72c10bf8b22b2ae6bad232e95b5c6e1d36da12392b4ec437d 8711bcc94973c";
        System.out.println(ss.length());

    }


}
