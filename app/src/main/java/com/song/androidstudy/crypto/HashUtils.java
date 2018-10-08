package com.song.androidstudy.crypto;

import java.security.MessageDigest;

public class HashUtils {


    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将byte转换为十六进制字符串
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String encodeHex(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * md5摘要
     *
     * @param str
     * @return
     */
    public static String md5Encode(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("utf-8"));
            return encodeHex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha1Encode(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes("utf-8"));
            return encodeHex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA256摘要处理
     *
     * @param str 明文
     * @return 哈希摘要字符串
     */
    public static String sha256Encode(String str) {
        try {
            // 将此换成MD5、SHA-1、SHA-224、SHA-256、SHA-384、SHA-512等参数
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("utf-8"));
            return encodeHex(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
