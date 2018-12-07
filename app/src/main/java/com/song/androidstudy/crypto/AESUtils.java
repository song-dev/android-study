package com.song.androidstudy.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES算法加密，java默认只支持128位
 */
public class AESUtils {

    /**
     * CBC模式使用偏移向量，长度和aes加密区块长度相等
     */
    private static final byte[] IV = "0000000000000000".getBytes();
    /**
     * 加密算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * AES算法
     */
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5PADDING";

    /**
     * 默认最高支持128位
     */
    private static final int KEY_LENGTH = 128;

    /**
     * AES加密字符串
     *
     * @param content 明文
     * @param aesKey  aes
     * @return 密文
     */
    public static byte[] encrypt(String content, String aesKey) {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(IV);
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            return cipher.doFinal(byteContent);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content 密文
     * @param aesKey  aes
     * @return 明文
     */
    public static String decrypt(byte[] content, String aesKey) {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(IV);
            SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(), ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 初始化为解密模式的密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过安全随机数生成key加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static byte[] encrypto(String content) {
        try {
            IvParameterSpec ivspec = new IvParameterSpec(IV);
            KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            // 不设置种子
            SecureRandom secureRandom = new SecureRandom();
            kgen.init(KEY_LENGTH, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            byte[] byteContent = content.getBytes("utf-8");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            return cipher.doFinal(byteContent);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过相同的种子生成相同的key加密
     *
     * @param content 待加密内容
     * @param aesKey  解密密钥
     * @return
     */
    public static byte[] encrypto(String content, String aesKey) {
        try {

            IvParameterSpec ivspec = new IvParameterSpec(IV);
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes());
            keygen.init(KEY_LENGTH, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            System.out.println(Base64.encode(raw));
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            // 8. 加密密
            return cipher.doFinal(content.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过相同的种子生成相同的key解密
     *
     * @param content 待解密内容
     * @param aesKey  解密密钥
     * @return
     */
    public static byte[] decrypto(byte[] content, String aesKey) {
        try {

            IvParameterSpec ivspec = new IvParameterSpec(IV);
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(aesKey.getBytes());
            keygen.init(KEY_LENGTH, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            System.out.println(Base64.encode(raw));
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, ALGORITHM);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            // 8. 解密
            return cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
