package com.song.androidstudy.crypto;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加密实现，java只支持PKCS8Padding 的公钥私钥对
 */
public class RSAUtils {

//    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMMaeTJ2vnGib79Gz+zCSOgvIf\n" +
//            "jfrWgrEf8IVDc4HSg/GwWZzELrZPAMSKB/iSn4csKtvG7mXd7/DfDNbmS3wlD6Xr\n" +
//            "xCgVB9FzuytxAxDQ5Pgygn28cHpaHIBjK3r+PWtCkU+0599QpuJoGJlrDvWCMtwn\n" +
//            "2nSODhtAXbxC+Rq4SwIDAQAB";
//
//    public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMwxp5Mna+caJvv0\n" +
//            "bP7MJI6C8h+N+taCsR/whUNzgdKD8bBZnMQutk8AxIoH+JKfhywq28buZd3v8N8M\n" +
//            "1uZLfCUPpevEKBUH0XO7K3EDENDk+DKCfbxwelocgGMrev49a0KRT7Tn31Cm4mgY\n" +
//            "mWsO9YIy3CfadI4OG0BdvEL5GrhLAgMBAAECgYEAgBb57taA2pTONakrol4+5GMC\n" +
//            "LYcr/do38Dg58IkJvBvmxeBA9dPzqvVpVMkRRjDqY2tzOIQdxK4aZGEygX6CncEC\n" +
//            "gwrvJ7Y4MbQhgGIOQfzU4XC/K2eHS4glOoEg008ja0TKTB2tOdz3FhO7ooahKQpp\n" +
//            "JgbRexApBfCzhwZBoOECQQD6N5iFo3bVN2zzV5EESQRGXq4bY3QQnEmpfxh8zKyv\n" +
//            "UgTdKuBVKw+pGmlPodOnPOl3kHuQ/Y3gH1L74mvYWLtRAkEA0OnDdxiBmDJL/5VT\n" +
//            "zWVhbtBs8qJscxrKPihZlNleoDiSrs9byYmO0mwyJy2KeeZXr8SmNz9hqPYhF+Ox\n" +
//            "d9ha2wJBAOqqqYjsPc7KJIw0W/VA5Zl5wqA9LeVGLm+gmz6wVmQ28Ajc5Xf64r0d\n" +
//            "4BanFvGJ1wwjnT/mDOFdf15sg+hrj4ECQFn/qqY+122ClXzojq6Ycy3y+kxYrpGz\n" +
//            "w9adOWJHdl1doctPJ0KeUPnThJOeKd6z3aip9dUtJ9xLFByfiY+QlCsCQC++Wd/J\n" +
//            "h2XRbikMQ9gPKR5DdlBwHLOwXKvTbpAO4aHVhgWbXPXZufniXW6fqXzloBjyEl8N\n" +
//            "JdTTt1R+gqMJTmI=";
    public static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoPwbWBuGqh+kaRT7dnSz\n" +
        "wlLSO9J/OPt40i9X/nCtsJUTokV5AgcOc4E4cQnW3nP/aoq/CCuZXfCcEDseh4nv\n" +
        "9hf62JHF2sOH3c04BIgrcqs0EHEsK2Xf/eHXOK8nwcZ+oUD2TL3ompjTyxeQduNs\n" +
        "GFrKZLXES2HgAx+x6eE1+iT9bQK+UYxgg12FYtHLkY+8Kfa9+qhfSfokTbHkNyry\n" +
        "w4VzER7hTAdwh2/ZGxjuyBfm6q/GqPpl1+vzunfhhIcXvfqi/qsJSpyLaJFkflPv\n" +
        "bm3JbJnlvEviBrnMUcWdFFbsNnFxKBFk/ivPBvtg28ZW0zpoDiWdWyCVpeA9HmUa\n" +
        "fQIDAQAB";

    public static final String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCg/BtYG4aqH6Rp\n" +
            "FPt2dLPCUtI70n84+3jSL1f+cK2wlROiRXkCBw5zgThxCdbec/9qir8IK5ld8JwQ\n" +
            "Ox6Hie/2F/rYkcXaw4fdzTgEiCtyqzQQcSwrZd/94dc4ryfBxn6hQPZMveiamNPL\n" +
            "F5B242wYWspktcRLYeADH7Hp4TX6JP1tAr5RjGCDXYVi0cuRj7wp9r36qF9J+iRN\n" +
            "seQ3KvLDhXMRHuFMB3CHb9kbGO7IF+bqr8ao+mXX6/O6d+GEhxe9+qL+qwlKnIto\n" +
            "kWR+U+9ubclsmeW8S+IGucxRxZ0UVuw2cXEoEWT+K88G+2DbxlbTOmgOJZ1bIJWl\n" +
            "4D0eZRp9AgMBAAECggEAPZqcAChwbPO03712yQq5q1g0OU5A/xZEUvJpEuYdoWA6\n" +
            "5jL9rofywMKnTaJ9Rh1PlX7IwaCljOxBXHizIxzm5d6hUHTgKOoAOSGKGTnenn/j\n" +
            "aZNVWroecDIXQQrOBPonhR+t4QofU+G90o8O6l/3Ao7juoxQNKScq7VaSOy00ibw\n" +
            "A6K8zc1fBYA1xgD6eiJ/iDa5WhbU5diXN7Fea2fabxzhEksfbFmiWskItD5ZTd1a\n" +
            "ibjQk3V31ubrrZCwOsVeALtUmTcGGP9meTWOv2wwwTPA8/A53uDLsRGF0TDUXHKp\n" +
            "7DM5MmxUMfUvHzKsZy8zy+EhG75EpsIf6TCe8LMVBQKBgQDU5jKTyqmsrSzsuK48\n" +
            "ZZeNPcz+C2Caa62hofF89NmNORtjeeBeGVdn21fLKqnP54BgCKCX37Ub+sGZ0iJy\n" +
            "q6GKw0c1rAJbrult3sP0EZjL5/3YrGb5yo1wXPjpW6m6mtC794AtJ8uE5BBppxGS\n" +
            "uTUI3vUEBucGbwL462l00I0YBwKBgQDBk18HemuMnq2BddEI8vBl71sBrKmpUil3\n" +
            "cx+McB4IbQ263/4n0ttT8zexhpGrEXrziJMaxrfgq1ql+H/Bozfg4s0YnQvsw2cX\n" +
            "8C+TrBnvr6veVtKSnYNV2S/I35BJw5df4Ggzw5+VvvAS0hwCzvXCVPMI57LfQ6t2\n" +
            "X+p3KNHwWwKBgQCa+/1jw4wjjWn/5i3I//gEtTGu2t93at3apYaj/acF/MWodAfW\n" +
            "2MHddDsERsmZ9SBqSL2H8i3o8XHQpb24FqOh3ajQNKj+z2VVO28RFWJTRk5wVokc\n" +
            "XMz8OGJnlvgHRT6hJ3ri5G5vDou4LgxH8JXgIFbjmJbEAzp5tP5vMIcWLwKBgQC4\n" +
            "pMsjhflqo70a3PfsklcO1Ja5bSpUsWOOZgi03Ak5vlQ3aguzjuTZGgmI07TFOaJP\n" +
            "BHpVMzIYIzsPT48FdJwLbit2i+7hA66l3dxz/tiqkaXeKnEnmwm47Lcw41dtlR0i\n" +
            "PhsHLVVe8EdtnG9Nmn4/xOoiF2i+oHzAKFOtIAnJOwKBgQC/WfJvrWIV26AE1MWt\n" +
            "X7nGtHKxtXOWZocNyxnE1n2GdouSroSCM6kS4fnkoOTBTiy25zTBplwJrAAyB4Ij\n" +
            "reem4skpCKaUPfueMkfnPDoT6/DLhCGeqo3FxNrWRSuUjKBrgJ9nGJ+lJkFCLeip\n" +
            "dwSVv71ffvsKM8ol8nziS3r2uQ==";

    private static final String RSA_TFM = "RSA/ECB/PKCS1Padding";

    /**
     * 每次加密的字节数，不能超过密钥的长度值减去11(NOPadding限制为秘钥长度), 否则需要手动切割填充
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        try {
            // 编码前设定编码方式及密钥
            Cipher cipher = Cipher.getInstance(RSA_TFM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static byte[] encrypt(byte[] data, PrivateKey privateKey) {
        try {
            // 编码前设定编码方式及密钥
            Cipher cipher = Cipher.getInstance(RSA_TFM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) {
        try {
            // 编码前设定编码方式及密钥
            Cipher cipher = Cipher.getInstance(RSA_TFM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static byte[] decrypt(byte[] data, PublicKey publicKey) {
        try {
            // 编码前设定编码方式及密钥
            Cipher cipher = Cipher.getInstance(RSA_TFM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥签名，签名生成字符串相同, 没有字节大小限制
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥验签
     *
     * @param data
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 公钥只支持 X509EncodedKeySpec
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥只支持 PKCS8EncodedKeySpec
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
