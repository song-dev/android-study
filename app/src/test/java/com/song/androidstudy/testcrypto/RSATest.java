package com.song.androidstudy.testcrypto;


import com.song.androidstudy.crypto.Base64;
import com.song.androidstudy.crypto.RSAUtils;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSATest {

    @Test
    public void test_pemToPkcs1() throws Exception {

        String publicKeyPKCS1 = "-----BEGIN RSA PUBLIC KEY-----\n" +
                "MIGJAoGBAIwzqZ6IdVhDvoLWTBpGvm/nSSOtmnBNNUUGtYq8S5WMCAuVS0QuWzR+\n" +
                "ccIEXbxquXrpq1xObt2N6CclBwtZCR42RFbtIMPLAVYIHuegVYm+FeLLG4yybbOd\n" +
                "TwGtRKlhMyJNKzB87VipsmNa0YftG6AasJZ1vZ3qP3mX8SYvo1cdAgMBAAE=\n" +
                "-----END RSA PUBLIC KEY-----";

        String pemToPkcs1 = RSAUtils.pemToPkcs1(publicKeyPKCS1);
        System.out.println("test_pemToPkcs1-->" + pemToPkcs1);
    }

    /**
     * 私钥加密
     *
     * @throws Exception
     */
    @Test
    public void test_private_encryption() throws Exception {

        String privateKey = "MIICegIBADANBgkqhkiG9w0BAQEFAASCAmQwggJgAgEAAoGBANYoDAaN3JLLoOi7\n" +
                "MR99qp47YmOes07r5F7SNDZ58lHkyWOwJIIBZzhcFaQbsK9DIFXc2P8lXRXWvBhI\n" +
                "AnQ98+CbHfxLEJYJKRzzgIQd0Qs7rrU1acdktbV0FyavmVxadVgKnmZcYr3Z/yV9\n" +
                "qldcV/xFRRmrroQ5e6kOWSzUFxiBAgMBAAECgYBnpGf1gBBgxsWv/HAQonvBKlI6\n" +
                "jiKPLmCOd0KnnqrkcUn5wdRZSp6Oyxn6GyCOIXlztHccPBPprsvHIIoJo5QFZXPm\n" +
                "Jm03tYYH9wfxM7cItPxvMWaMMEVC2acWB/wSPpkpwagI2MjiM+UTvS/XLaDUeuRi\n" +
                "uvyLyCKOClKjLGJWpQJFAOMESQ4LQi5H8oA4Cx/ux0jS3t+kAFB97C+1S1l2LY7W\n" +
                "U2mXcGZKgX7coOHNJavIJB+M9D8jrBbByTKRTrdw4t252ZzzAj0A8X9w6Is2IyaF\n" +
                "EOs5G5LUc+sSdr1n8lx5nlP797qHCrclFeYd81S9chnm4JP2HDTnoB/42rgfpebk\n" +
                "iYG7AkRGZrnRJSSs/ZrysG6ixIYAy4Rajp2aWLK6SNrJL6EJPuY3RMOStWBbW0h5\n" +
                "PgmEsFijgC7utb1UgVxH1lA3C0cPw+b8iQI8YLqU9O/drVKeddphs2OqaQu9B4Zx\n" +
                "/ieAJySohd9We7pDxnO3h2FnyC4LxeQ97apbJE3qFWEue/rxiwpFAkUAuHnA330V\n" +
                "NbS1EZP+nCix5eogOXVlktHh2jpLguMMs9H29UHLkPTDHORa3p+BKdblxpQaA+A4\n" +
                "sbdaopYC5zpJd3T0ofA=";

        String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWKAwGjdySy6DouzEffaqeO2Jj\n" +
                "nrNO6+Re0jQ2efJR5MljsCSCAWc4XBWkG7CvQyBV3Nj/JV0V1rwYSAJ0PfPgmx38\n" +
                "SxCWCSkc84CEHdELO661NWnHZLW1dBcmr5lcWnVYCp5mXGK92f8lfapXXFf8RUUZ\n" +
                "q66EOXupDlks1BcYgQIDAQAB\n" +
                "-----END PUBLIC KEY-----\n";

        String s = "{\"mobile\":{\"geetest\":\"eyJ0eXAiOiJKV1QtQ2xhaW0iLCJhbGciOiJSUzI1NiIsImtpZCI6IjViZDE4ZjY1ZDM0NWJiMTkwODJlZWEzNy1rZXkxIn0.eyJ2ZXIiOiIiLCJtb2RhbCI6ImVtYWlsIiwiaXNzIjoiNWJkMThmNjVkMzQ1YmIxOTA4MmVlYTM3Iiwic3ViIjpudWxsLCJpYXQiOjE1NDE3NTQ2MzQsImV4cCI6MTU3MzI5NDcxNywianRpIjoiZDI2Mzk4OGNlNDA4MTFlOGE5MmQwMDE2M2UwNWIxNTgiLCJjbGFpbV9jb250ZW50Ijp7Im1vYmlsZV9ubyI6IjE4MzAxMTc3MTEzIn0sImNsYWltX2V4cGxhbmF0aW9uIjp7Im1vYmlsZV9ubyI6Ilx1NjI0Ylx1NjczYVx1NTNmN1x1NzgwMSJ9LCJyZXZva2VfbWV0aG9kIjp7fX0.inFaizMMtVBQ1a0bNNGsXnUt7qLTMep3re303vmGQJxo3L9jUXwUr4Wv2VK-qvPf1h6IY3KrJD8UvtvKKSPhW5a7erwEf9bU87oF_f1BPSE-d77BGA1vxHV1PQZglbXM1S3zzRARKNnwRQZeNmXye57a2OB3tqpo-UoQSjJQrcM\"}}";
        String encode = Base64.encode(s.getBytes());
        System.out.println("原始数据s-->" + s);

        String encipher = RSAUtils.encipher(encode, RSAUtils.getPrivateKey(privateKey), RSAUtils.MAX_ENCRYPT_BLOCK);
        System.out.println();
        System.out.println("私钥加密数据encipher-->" + encipher);

        String pemToPkcs1 = RSAUtils.pemToPkcs1(publicKey);
        String decipher = RSAUtils.decipher(encipher, RSAUtils.getPublicKey(pemToPkcs1), RSAUtils.MAX_DECRYPT_BLOCK);
        System.out.println();
        System.out.println("公钥解密数据decipher-->" + new String(Base64.decode(decipher)));

    }

    /**
     * 公钥加密，私钥解密测试
     *
     * @throws Exception
     */
    @Test
    public void test_rsa_public_encipher() throws Exception {
        // 公钥加密
        String data = "\"mobile\":{\"geetest\":\"eyJ0eXAiOiJKV1QtQ2xhaW0iLCJhbGciOiJSUzI1NiIsImtpZCI6IjViZDE4ZjY1ZDM0NWJiMTkwODJlZWEzNy1rZXkxIn0.eyJ2ZXIiOiIiLCJtb2RhbCI6ImVtYWlsIiwiaXNzIjoiNWJkMThmNjVkMzQ1YmIxOTA4MmVlYTM3Iiwic3ViIjpudWxsLCJpYXQiOjE1NDE3NTg5ODgsImV4cCI6MTU3MzQ2ODUxMCwianRpIjoiNzZlYjZkMDRlNTlkMTFlOGE5MmQwMDE2M2UwNWIxNTgiLCJjbGFpbV9jb250ZW50Ijp7Im1vYmlsZV9ubyI6IjEzODcxMDY4NzA0In0sImNsYWltX2V4cGxhbmF0aW9uIjp7Im1vYmlsZV9ubyI6Ilx1NjI0Ylx1NjczYVx1NTNmN1x1NzgwMSJ9LCJyZXZva2VfbWV0aG9kIjp7fX0.C9KNkSBjY5QQb7KimnhvlewcViYbjX4TTN-2KvmGQLnMHNXbycyljff_yIp2wzNEriPjz7FAcfzDV-LOZ8pGYH0EMBlI4NM7JY77e4jVAegNC4fxv604HRHtel6HDZf6Vmngd3RGTlVtg1mzo1cV4XRIrB4sAlX89zXIR0nxMcI\"}}\n";
        // 公钥
        String publciKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWKAwGjdySy6DouzEffaqeO2Jj\n" +
                "nrNO6+Re0jQ2efJR5MljsCSCAWc4XBWkG7CvQyBV3Nj/JV0V1rwYSAJ0PfPgmx38\n" +
                "SxCWCSkc84CEHdELO661NWnHZLW1dBcmr5lcWnVYCp5mXGK92f8lfapXXFf8RUUZ\n" +
                "q66EOXupDlks1BcYgQIDAQAB\n" +
                "-----END PUBLIC KEY-----";
        System.out.println("原始数据data-->" + data);
        String encipher = RSAUtils.encipher(data, RSAUtils.pemToPkcs1(publciKey), RSAUtils.MAX_ENCRYPT_BLOCK);
        System.out.println("公钥加密数据encipher-->" + encipher);
        System.out.println();

        String privateKey = "MIICegIBADANBgkqhkiG9w0BAQEFAASCAmQwggJgAgEAAoGBANYoDAaN3JLLoOi7\n" +
                "MR99qp47YmOes07r5F7SNDZ58lHkyWOwJIIBZzhcFaQbsK9DIFXc2P8lXRXWvBhI\n" +
                "AnQ98+CbHfxLEJYJKRzzgIQd0Qs7rrU1acdktbV0FyavmVxadVgKnmZcYr3Z/yV9\n" +
                "qldcV/xFRRmrroQ5e6kOWSzUFxiBAgMBAAECgYBnpGf1gBBgxsWv/HAQonvBKlI6\n" +
                "jiKPLmCOd0KnnqrkcUn5wdRZSp6Oyxn6GyCOIXlztHccPBPprsvHIIoJo5QFZXPm\n" +
                "Jm03tYYH9wfxM7cItPxvMWaMMEVC2acWB/wSPpkpwagI2MjiM+UTvS/XLaDUeuRi\n" +
                "uvyLyCKOClKjLGJWpQJFAOMESQ4LQi5H8oA4Cx/ux0jS3t+kAFB97C+1S1l2LY7W\n" +
                "U2mXcGZKgX7coOHNJavIJB+M9D8jrBbByTKRTrdw4t252ZzzAj0A8X9w6Is2IyaF\n" +
                "EOs5G5LUc+sSdr1n8lx5nlP797qHCrclFeYd81S9chnm4JP2HDTnoB/42rgfpebk\n" +
                "iYG7AkRGZrnRJSSs/ZrysG6ixIYAy4Rajp2aWLK6SNrJL6EJPuY3RMOStWBbW0h5\n" +
                "PgmEsFijgC7utb1UgVxH1lA3C0cPw+b8iQI8YLqU9O/drVKeddphs2OqaQu9B4Zx\n" +
                "/ieAJySohd9We7pDxnO3h2FnyC4LxeQ97apbJE3qFWEue/rxiwpFAkUAuHnA330V\n" +
                "NbS1EZP+nCix5eogOXVlktHh2jpLguMMs9H29UHLkPTDHORa3p+BKdblxpQaA+A4\n" +
                "sbdaopYC5zpJd3T0ofA=";

        // 私钥解密逻辑
        String decipher = RSAUtils.decipher(encipher, privateKey, RSAUtils.MAX_DECRYPT_BLOCK);
        System.out.println("解密数据decipher-->" + decipher);
    }

    @Test
    public void test_sign() throws Exception {

        String privateKey = "MIICegIBADANBgkqhkiG9w0BAQEFAASCAmQwggJgAgEAAoGBANYoDAaN3JLLoOi7\n" +
                "MR99qp47YmOes07r5F7SNDZ58lHkyWOwJIIBZzhcFaQbsK9DIFXc2P8lXRXWvBhI\n" +
                "AnQ98+CbHfxLEJYJKRzzgIQd0Qs7rrU1acdktbV0FyavmVxadVgKnmZcYr3Z/yV9\n" +
                "qldcV/xFRRmrroQ5e6kOWSzUFxiBAgMBAAECgYBnpGf1gBBgxsWv/HAQonvBKlI6\n" +
                "jiKPLmCOd0KnnqrkcUn5wdRZSp6Oyxn6GyCOIXlztHccPBPprsvHIIoJo5QFZXPm\n" +
                "Jm03tYYH9wfxM7cItPxvMWaMMEVC2acWB/wSPpkpwagI2MjiM+UTvS/XLaDUeuRi\n" +
                "uvyLyCKOClKjLGJWpQJFAOMESQ4LQi5H8oA4Cx/ux0jS3t+kAFB97C+1S1l2LY7W\n" +
                "U2mXcGZKgX7coOHNJavIJB+M9D8jrBbByTKRTrdw4t252ZzzAj0A8X9w6Is2IyaF\n" +
                "EOs5G5LUc+sSdr1n8lx5nlP797qHCrclFeYd81S9chnm4JP2HDTnoB/42rgfpebk\n" +
                "iYG7AkRGZrnRJSSs/ZrysG6ixIYAy4Rajp2aWLK6SNrJL6EJPuY3RMOStWBbW0h5\n" +
                "PgmEsFijgC7utb1UgVxH1lA3C0cPw+b8iQI8YLqU9O/drVKeddphs2OqaQu9B4Zx\n" +
                "/ieAJySohd9We7pDxnO3h2FnyC4LxeQ97apbJE3qFWEue/rxiwpFAkUAuHnA330V\n" +
                "NbS1EZP+nCix5eogOXVlktHh2jpLguMMs9H29UHLkPTDHORa3p+BKdblxpQaA+A4\n" +
                "sbdaopYC5zpJd3T0ofA=";

        String sign = RSAUtils.sign(privateKey.getBytes(), privateKey);
        System.out.println(sign);

    }

    @Test
    public void test_smid() {
        String key =
                "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMUBVv"
                        + "+BdK8bzgV8iTEe25zWhQabmsC8RCo4TAMW79i6ReUymlcmAvTjxq5pxKFyfvRmdsdOL9RDEQlB+6Z/nP8CAwEAAQ==";

        String s = RSAUtils.encipher("267000:3F45E0:EDC3C4feq#%dc87#989(^)78909-=89+1vfiqocxq58*@#~09$", key, RSAUtils.MAX_ENCRYPT_BLOCK);
        System.out.println(s);

    }

    @Test
    public void test_mobile() {

        String data = "{\"data\":{\"custVerification\":{\"apId\":\"451330\",\"apKey\":\"6D089C2BCF970752E0540024817812F6\"}}}";

        try {
            String s = encryptByPublicKey(data, publicKey_1);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String encryptData = "H76cNddi/B2U9A8xJNyzMvnrSq/eu0utKrSTRBZIV1V5j3V3SjIhn7joAcaz4OgAYORYG5EJqEaXHiFtsrKwgZ2BASjsE9fgp30jyz/YTHfJhxTQoLtFsjxFDXXCSG5khZUYLCoqGrdaooiOjz0lpdCHwc1s/VWpNsc9wpXizQ0=";
        try {
            String s = decryptByPublicKey(encryptData, publicKey_1);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String publicKey_1 =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSm8IcHZdD6H7fLgvHBKUrIgB8F/TP3RGkEwQDtV5WCo92UzEt/Lx2nu2ttpqH5YyRPxMJ7iElIvhrq1Fty84arvC+rWl4Z6156T4K1mf7Qhdesep0ji3OR0aaZ9S7TQ8bKqMb7VTxpLp7r/pdDbAo8UvRIJTfNZwAJjrsDoDVhwIDAQAB";


    //公钥解密
    public String decryptByPublicKey(String param, String publicKey)
            throws Exception {
        byte[] bates = Base64.decode(param);
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = bates.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(bates, offSet, 128);
            } else {
                cache = cipher.doFinal(bates, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String pubDecode = new String(decryptedData, "UTF-8");
        return pubDecode;
    }

    //公钥加密
    public String encryptByPublicKey(String param, String publicKey)
            throws Exception {

        byte[] data = param.getBytes("UTF-8");
        if (data == null || publicKey == null) {
            throw new NullPointerException("encryptByPublicKey data||publicKey is null");
        }

        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;

        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 117;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encode(encryptedData);
    }

}